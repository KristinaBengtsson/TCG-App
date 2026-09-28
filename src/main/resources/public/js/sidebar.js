document.addEventListener("DOMContentLoaded", function () {

    // MENU
    const menuButtons = document.querySelectorAll(".menu-button");

    menuButtons.forEach((button) => {
        button.addEventListener("click", function () {

            const pageName = button.textContent.trim();

            switch (pageName) {

                case "Dashboard":
                    window.location.href = "/dashboard";
                    break;

                case "Collection":
                    window.location.href = "/collection";
                    break;

                case "Master Sets":
                    window.location.href = "/mastersets";
                    break;

                case "Wishlist":
                    window.location.href = "/wishlist";
                    break;
            }

        });
    });


    // PROFIL
    const profileButton = document.getElementById("profileButton");

    if (profileButton) {

        profileButton.addEventListener("click", function () {

            window.location.href = "profile.html";

        });

    }

});