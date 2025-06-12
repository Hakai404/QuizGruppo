function openModal() {
  document.getElementById("quizModal").style.display = "block";
}

function closeModal() {
  document.getElementById("quizModal").style.display = "none";
}

document.addEventListener("DOMContentLoaded", () => {
  const bandieraLink = document.getElementById("bandiera");
  bandieraLink.addEventListener("click", (e) => {
    e.preventDefault(); // blocca il link di default
    window.location.href = "/quiz-bandiera"; // indirizza alla pagina quiz_bandiera
  });
});