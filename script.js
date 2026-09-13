document.addEventListener("DOMContentLoaded", function () {
    const menuButtons = document.querySelectorAll(".menu-button");

    menuButtons.forEach((button) => {
        button.addEventListener("click", function () {
            const pageName = button.textContent.trim();

            switch (pageName) {
                case "Dashboard":
                    window.location.href = "dashboard.html";
                    break;

                case "Collection":
                    window.location.href = "collection.html";
                    break;

                case "Master Sets":
                    window.location.href = "mastersets.html";
                    break;

                case "Wishlist":
                    window.location.href = "wishlist.html";
                    break;

                default:
                    console.log("Siden findes ikke endnu.");
            }
        });
    });
});