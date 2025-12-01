document.querySelector("form").addEventListener("submit", function (e) {
  const pass = document.querySelector("input[name='newPassword']").value;
  const confirm = document.querySelector("input[name='confirmPassword']").value;

  if (pass !== confirm) {
    e.preventDefault();
    alert("As senhas não coincidem!");
  }
});
