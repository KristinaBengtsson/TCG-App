(() => {
    const form = document.getElementById("card-search-form");
    const results = document.getElementById("card-results");
    const status = document.getElementById("search-status");
    const retry = document.getElementById("search-retry");
    const pagination = document.querySelector(".search-pagination");
    const previous = document.getElementById("previous-page");
    const next = document.getElementById("next-page");
    const pageLabel = document.getElementById("page-label");
    const dialog = document.getElementById("card-dialog");
    const detail = document.getElementById("card-detail");
    const detailTitle = document.getElementById("card-detail-title");
    const pageSize = 24;
    let currentPage = 1;
    let requestedPage = 1;
    let activeSearch = { q: "", field: "name", language: "en" };
    let searchRequest;
    let detailRequest;

    function element(tag, text, className) {
        const node = document.createElement(tag);
        if (text !== undefined) node.textContent = text;
        if (className) node.className = className;
        return node;
    }

    function cardImage(card, quality = "low") {
        const wrapper = element("div", undefined, "search-image");
        const fallback = () => wrapper.replaceChildren(element("span", "Billede mangler"));
        // API-data indsættes som tekst; billeder må kun hentes fra TCGdex.
        if (typeof card.image !== "string" || !card.image.startsWith("https://assets.tcgdex.net/")) {
            fallback();
            return wrapper;
        }
        const image = document.createElement("img");
        image.alt = card.name || "Pokémon-kort";
        image.loading = "lazy";
        image.width = 245;
        image.height = 337;
        image.src = `${card.image}/${quality}.webp`;
        image.addEventListener("error", fallback, { once: true });
        wrapper.append(image);
        return wrapper;
    }

    async function fetchJson(url, controller) {
        const timer = setTimeout(() => controller.abort("timeout"), 20000);
        try {
            const response = await fetch(url, { signal: controller.signal });
            if (!response.ok) {
                const message = await response.text();
                throw new Error(message || "Kunne ikke hente kort. Prøv igen.");
            }
            return await response.json();
        } catch (error) {
            if (controller.signal.reason === "timeout") {
                throw new Error("Søgningen tog for lang tid. Prøv igen.");
            }
            if (error instanceof TypeError || error instanceof SyntaxError) {
                throw new Error("Kunne ikke hente kort. Tjek forbindelsen og prøv igen.");
            }
            throw error;
        } finally {
            clearTimeout(timer);
        }
    }

    async function search(page) {
        searchRequest?.abort();
        const controller = new AbortController();
        searchRequest = controller;
        requestedPage = page;
        const searchState = { ...activeSearch };
        const params = new URLSearchParams({ ...searchState, page });
        results.replaceChildren();
        results.setAttribute("aria-busy", "true");
        pagination.hidden = true;
        retry.hidden = true;
        status.textContent = "Henter kort …";
        try {
            const cards = await fetchJson(`/api/pokemon/cards?${params}`, controller);
            if (searchRequest !== controller) return;
            if (!Array.isArray(cards) || cards.some(card => !card || typeof card.id !== "string")) {
                throw new Error("Kortdatabasen gav et uventet svar. Prøv igen.");
            }
            currentPage = page;
            for (const card of cards) {
                const button = element("button", undefined, "search-card");
                button.type = "button";
                button.setAttribute("aria-label", `Se ${card.name}, ${card.id}`);
                button.append(cardImage(card), element("h2", card.name),
                    element("p", `Kortnummer: ${card.localId ?? "Ukendt"}`), element("p", card.id));
                button.addEventListener("click", () => showCard(card, searchState.language));
                results.append(button);
            }
            status.textContent = cards.length
                ? `Viser ${cards.length} kort på side ${page}. Vælg et kort for at se detaljer.`
                : page > 1 ? "Der er ikke flere kort. Gå tilbage til forrige side."
                    : "Ingen kort fundet. Prøv et andet navn, søgefelt eller kortsprog.";
            pageLabel.textContent = `Side ${page}`;
            previous.disabled = page === 1;
            next.disabled = cards.length < pageSize || page >= 10000;
            pagination.hidden = cards.length === 0 && page === 1;
        } catch (error) {
            if (searchRequest !== controller || error.name === "AbortError") return;
            status.textContent = error.message;
            retry.hidden = false;
        } finally {
            if (searchRequest === controller) results.setAttribute("aria-busy", "false");
        }
    }

    async function showCard(card, language) {
        detailRequest?.abort();
        const controller = new AbortController();
        detailRequest = controller;
        detailTitle.textContent = card.name;
        detail.replaceChildren(element("p", "Henter kortdetaljer …"));
        if (!dialog.open) dialog.showModal();
        try {
            const data = await fetchJson(`/api/pokemon/cards/${encodeURIComponent(card.id)}?language=${encodeURIComponent(language)}`, controller);
            if (detailRequest !== controller) return;
            if (!data || typeof data.id !== "string") throw new Error("Kortdetaljerne kunne ikke læses.");
            const layout = element("div", undefined, "card-detail-layout");
            const list = element("dl");
            const variants = Object.entries(data.variants || {}).filter(([, available]) => available === true)
                .map(([name]) => name).join(", ");
            const fields = [
                ["Sæt", data.set?.name], ["Kort-ID", data.id],
                ["Kortnummer", data.set?.cardCount?.official ? `${data.localId}/${data.set.cardCount.official}` : data.localId],
                ["Sjældenhed", data.rarity], ["Kategori", data.category],
                ["Type", data.types?.join(", ")], ["HP", data.hp],
                ["Pokédex-nummer", data.dexId?.join(", ")], ["Kunstner", data.illustrator],
                ["Registrerede varianter", variants], ["Kortsprog", language]
            ];
            for (const [label, value] of fields) {
                list.append(element("dt", label), element("dd", value === undefined || value === null || value === "" ? "Ikke oplyst" : value));
            }
            layout.append(cardImage(data, "high"), list);
            detail.replaceChildren(layout);
        } catch (error) {
            if (detailRequest !== controller || error.name === "AbortError") return;
            const tryAgain = element("button", "Prøv igen");
            tryAgain.type = "button";
            tryAgain.addEventListener("click", () => showCard(card, language));
            detail.replaceChildren(element("p", error.message), tryAgain);
        }
    }

    dialog.addEventListener("close", () => {
        detailRequest?.abort();
        detailRequest = null;
    });
    form.addEventListener("submit", event => {
        event.preventDefault();
        activeSearch = Object.fromEntries(new FormData(form));
        activeSearch.q = activeSearch.q.trim();
        search(1);
    });
    previous.addEventListener("click", () => search(currentPage - 1));
    next.addEventListener("click", () => search(currentPage + 1));
    retry.addEventListener("click", () => search(requestedPage));
    search(1);
})();
