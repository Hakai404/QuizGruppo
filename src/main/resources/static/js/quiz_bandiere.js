function showNextButton() {
  const nextButton = document.getElementById("next");
  if (nextButton) {
    nextButton.style.display = "block";
  }
}

document.addEventListener('DOMContentLoaded', function() {
  // Nasconde il pulsante Next all'inizio
  const nextButton = document.getElementById("next");
  if (nextButton) {
    nextButton.style.display = "none";
  }

  // Gestione click sulle carte
  document.querySelectorAll(".card").forEach((card) => {
    card.addEventListener("click", () => {
      // Seleziona la carta cliccata
      document.querySelectorAll(".card").forEach(c => c.classList.remove("selected"));
      card.classList.add("selected");
      
      // Seleziona automaticamente il radio button corrispondente
      const radioButton = card.previousElementSibling;
      if (radioButton && radioButton.type === 'radio') {
        radioButton.checked = true;
      }

      // Mostra tutte le risposte (corrette/sbagliate)
      const cards = document.querySelectorAll(".card");
      cards.forEach((c) => {
        const isCorrect = c.getAttribute("data-correct") === "true";
        if (isCorrect) {
          c.classList.add("correct");
        } else {
          c.classList.add("incorrect");
        }
        // Disabilita ulteriori click
        c.style.pointerEvents = "none";
      });
      
      showNextButton();
      if (typeof interval !== 'undefined') {
        clearInterval(interval);
      }
    });
  });

  // Avvia il timer
  startTimer();
});

let interval;

function startTimer() {
  // Timer fisso di 10 secondi
  const duration = 10;

  const display = document.querySelector("#time");
  if (!display) {
    console.error("Elemento #time non trovato");
    return;
  }

  let timer = duration,
  minutes, seconds;
  
  interval = setInterval(function () {
    minutes = parseInt(timer / 60, 10);
    seconds = parseInt(timer % 60, 10);

    minutes = minutes < 10 ? "0" + minutes : minutes;
    seconds = seconds < 10 ? "0" + seconds : seconds;

    display.textContent = minutes + ":" + seconds;

    if (--timer < 0) {
      clearInterval(interval);
      
      // Tempo scaduto
      const cards = document.querySelectorAll(".card");
      cards.forEach((c) => {
        const isCorrect = c.getAttribute("data-correct") === "true";
        if (isCorrect) {
          c.classList.add("correct");
        } else {
          c.classList.add("incorrect");
        }
        // Disabilita ulteriori click
        c.style.pointerEvents = "none";
      });
      
      showNextButton();
      
      // Mostra messaggio di timeout
      setTimeout(() => {
        alert("Tempo scaduto! La risposta corretta è evidenziata in verde.");
      }, 500);
    }
  }, 1000);
}

window.onload = function () {

}