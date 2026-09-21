document.addEventListener("DOMContentLoaded", () => {
    const pages = {
        Dashboard: "dashboard.html",
        Collection: "collection.html",
        "Master Sets": "mastersets.html",
        Wishlist: "wishlist.html"
    };

    document.querySelectorAll(".menu-button").forEach((button) => {
        button.addEventListener("click", () => {
            const destination = pages[button.textContent.trim()];
            if (destination) {
                window.location.href = destination;
            }
        });
    });

    document.getElementById("profileButton")?.addEventListener("click", () => {
        window.location.href = "profile.html";
    });
});