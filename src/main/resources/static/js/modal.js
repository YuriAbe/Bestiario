function showConfirmModal(title, message) {
  return new Promise((resolve) => {
    // Crates the HTML of modal
    const modalHtml = `
        <div class="modal fade" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">${title}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
                    </div>
                    <div class="modal-body">
                        <p>${message}</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-action="cancel">Cancelar</button>
                        <button type="button" class="btn btn-warning" data-action="ok">OK</button>
                    </div>
                </div>
            </div>
        </div>`;
    // Converts string into DOM element
    const wrapper = document.createElement("div");
    wrapper.innerHTML = modalHtml;
    const modalElement = wrapper.firstElementChild;
    // Add to the body
    document.body.appendChild(modalElement);
    // Instantiates the Modal from Bootstrap
    const modal = new bootstrap.Modal(modalElement);
    // Deal with buttons
    modalElement
      .querySelector('[data-action="ok"]')
      .addEventListener("click", () => {
        resolve(true);
        modal.hide();
      });
    modalElement
      .querySelector('[data-action="cancel"]')
      .addEventListener("click", () => {
        resolve(false);
        modal.hide();
      });
    // If the user click in X button, or outside of modal
    modalElement.addEventListener("hidden.bs.modal", () => {
      resolve(false);
      modalElement.remove(); // remove do DOM
    });
    // show the modal
    modal.show();
  });
}
