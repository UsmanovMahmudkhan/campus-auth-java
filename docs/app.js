const menuButton = document.querySelector(".menu-button");
const sidebar = document.querySelector("#sidebar");

menuButton?.addEventListener("click", () => {
  const isOpen = sidebar.classList.toggle("is-open");
  menuButton.setAttribute("aria-expanded", String(isOpen));
});

sidebar?.addEventListener("click", (event) => {
  if (event.target instanceof HTMLAnchorElement) {
    sidebar.classList.remove("is-open");
    menuButton?.setAttribute("aria-expanded", "false");
  }
});

const currentPage = document.body.dataset.page;

if (currentPage) {
  document.querySelectorAll("[data-nav]").forEach((link) => {
    link.classList.toggle("is-active", link.getAttribute("data-nav") === currentPage);
  });
}

document.querySelectorAll("[data-copy]").forEach((button) => {
  button.addEventListener("click", async () => {
    const original = button.textContent;

    try {
      await navigator.clipboard.writeText(button.getAttribute("data-copy"));
      button.textContent = "Copied";
    } catch {
      button.textContent = "Select";
    }

    setTimeout(() => {
      button.textContent = original;
    }, 1400);
  });
});
