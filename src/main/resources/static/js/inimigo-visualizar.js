document.addEventListener("DOMContentLoaded", () => {
  const botoes = document.querySelectorAll(".btn-visualizar");

  if (!botoes || botoes.length === 0) return;

  botoes.forEach((btn) => {
    btn.addEventListener("click", async () => {
      const id = btn.dataset.id;

      try {
        const response = await fetch(`/inimigos/${id}`);

        if (!response.ok) {
          throw new Error("Erro ao buscar inimigo");
        }

        const data = await response.json();

        // Preencher modal
        document.querySelector("#modal-nome").textContent = data.nome;
        document.querySelector("#modal-especie").textContent = data.especie;
        document.querySelector("#modal-dificuldade").textContent =
          data.dificuldade;
        document.querySelector("#modal-ataque").textContent =
          data.ataque_especial;

        // Exibir modal
        const modal = new bootstrap.Modal(
          document.getElementById("modalInimigo")
        );
        modal.show();
      } catch (error) {
        console.error("Erro ao carregar inimigo:", error);
        alert("Erro ao carregar inimigo.");
      }
    });
  });
});
