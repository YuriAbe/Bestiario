document.querySelectorAll(".delete-form").forEach((form) => {
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const confirm = await showConfirmModal(
      "Confirme",
      "Deseja excluir esse Jogo?"
    );
    if (confirm) {
      form.submit();
    }
  });
});
