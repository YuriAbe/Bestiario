document.addEventListener("DOMContentLoaded", () => {
  const botoes = document.querySelectorAll(".btn-visualizar");

  if (!botoes || botoes.length === 0) return;

  botoes.forEach((btn) => {
    btn.addEventListener("click", async () => {
      const id = btn.dataset.id;

      try {
        const response = await fetch(`/jogos/${id}`);

        if (!response.ok) {
          throw new Error("Erro ao buscar jogo");
        }

        const data = await response.json();

        // Preencher modal
        document.querySelector("#modal-titulo").textContent = data.titulo;
        document.querySelector("#modal-genero").textContent = data.genero;
        document.querySelector("#modal-estudio").textContent = data.estudio;

        // Exibir modal
        const modal = new bootstrap.Modal(
          document.getElementById("modalJogo")
        );
        modal.show();
      } catch (error) {
        console.error("Erro ao carregar jogo:", error);
        alert("Erro ao carregar jogo.");
      }
    });
  });
});
