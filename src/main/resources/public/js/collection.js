
// Denne kode styrede menuknapperne og profilknappen med hårdkodede .html-adresser.
// Fjernet fordi sidebar.js nu klarer navigationen via routes i PageController (/dashboard, /collection osv.),
// og denne kode overskrev de nye adresser, så man endte på en side, der ikke findes.
/*
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

 */