document.querySelectorAll(".delete-form").forEach((form) => {
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const confirm = await showConfirmModal(
      "Confirme",
      "Deseja excluir esse Inimigo?"
    );
    if (confirm) {
      form.submit();
    }
  });
});
