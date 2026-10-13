//FIRST TRY (Udskriver kun hvad der sker i Console)
/*const customset = document.querySelector("#customSet-button"); //Find id customSet-button
customset.addEventListener("click", function (){ //"Lyt efter" et klik funktionen startes
    console.log("User want to create a new custom set"); //funktionen der skal køres
});
const officialset = document.querySelector("#officialSet-button");
officialset.addEventListener("click", function (){
   console.log("User want to add a official masterset to tacking");
});*/

//SECOND TRY
function clickedButton(activeButton, inactiveButton){ //funktion der skifter styling på knappen
    activeButton.classList.remove("outline"); //fjern klassen "outline"
    activeButton.classList.add("primary"); //tilføj klassen "primary"
    inactiveButton.classList.remove("primary"); //fjern klassen "primary"
    inactiveButton.classList.add("outline"); //tilføj klassen "outline"
}

const customset = document.querySelector("#customSet-button"); //Find id customSet-button
const officialset = document.querySelector("#officialSet-button"); //Find id officialSet-button

customset.addEventListener("click", function () { //"Lyt efter" et klik funktionen startes
clickedButton(customset, officialset); // Gør customset til den valgte, officialset til den ikke valgte
});
officialset.addEventListener("click", function () { //"Lyt efter" et klik funktionen startes
    clickedButton(officialset, customset); // 2 parametre. Gør officialset til den valgte, customset til den ikke valgte
});
