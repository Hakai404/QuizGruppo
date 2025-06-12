function showNextButton() {
  const nextButton = document.getElementById("next");
  nextButton.style.display = "block";
}


document.querySelectorAll(".card").forEach((card) => {
  card.addEventListener("click", () => {
    const cards = document.querySelectorAll(".card");
    cards.forEach((c) => {
      const isCorrect = c.getAttribute("corretto") === "true";
      if (isCorrect) {
        c.classList.add("correct");
      } else {
        c.classList.add("incorrect");
      }
    });
    showNextButton();
    clearInterval(interval);
  });
});

let interval;
function startTimer(duration, display) {
  let timer = duration,
    minutes,
    seconds;
  interval = setInterval(function () {
    minutes = parseInt(timer / 60, 10);
    seconds = parseInt(timer % 60, 10);

    minutes = minutes < 10 ? "0" + minutes : minutes;
    seconds = seconds < 10 ? "0" + seconds : seconds;

    display.textContent = minutes + ":" + seconds;

    if (--timer < 0) {
      clearInterval(interval);
      alert("Time's up!");
      const cards = document.querySelectorAll(".card");
      cards.forEach((c) => {
        const isCorrect = c.getAttribute("corretto") === "true";
        if (isCorrect) {
          c.classList.add("missed"); // puoi definire uno stile per "missed"
        } else {
          c.classList.add("incorrect");
        }

      });
      showNextButton();
    }
  }, 1000);
  
}

window.onload = function () {
  document.getElementById("next").style.display = "none";

  let duration;
  let difficulty = document.getElementById("difficulty").value;
  switch (difficulty) {
    case "easy":
      duration = 30; // 5 seconds for easy
      break;
    case "medium":
      duration = 15; // 15 seconds for medium
      break;
    case "hard":
      duration = 5; // 30 seconds for hard
      break;
    default:
      duration = 5; // default to 5 seconds
  }
  let display = document.querySelector("#time");
  startTimer(duration, display);
};



