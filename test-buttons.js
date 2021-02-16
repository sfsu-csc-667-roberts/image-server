const sendRequest = (method) => {
  fetch("http://localhost:8080", { method })
    .then((response) => console.log(response))
    .catch((error) => console.error(error));
};

const handler = (method) => (_event) => sendRequest(method);

document
  .querySelector("#delete-button")
  .addEventListener("click", handler("delete"));

document.querySelector("#get-button").addEventListener("click", handler("get"));

document.querySelector("#put-button").addEventListener("click", handler("put"));

document
  .querySelector("#post-button")
  .addEventListener("click", handler("post"));
