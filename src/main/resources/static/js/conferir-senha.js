const pass = document.querySelector("#password");
const confirm = document.querySelector("#confirmPassword");
const errorText = document.querySelector("#confirmPassError");

document.querySelector("form").addEventListener("submit", function (e) {
  if (pass.value !== confirm.value) {
    e.preventDefault();
    errorText.classList.remove("d-none");
  }
});

confirm.addEventListener("input", () => {
  if (pass.value === confirm.value) {
    errorText.classList.add("d-none");
  }
});
