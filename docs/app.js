const menuButton = document.querySelector(".menu-button");
const sidebar = document.querySelector("#sidebar");
const navLinks = [...document.querySelectorAll(".sidebar a, .toc a")];
const sections = navLinks
  .map((link) => document.querySelector(link.getAttribute("href")))
  .filter(Boolean);

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

document.querySelectorAll("[data-copy]").forEach((button) => {
  button.addEventListener("click", async () => {
    const original = button.textContent;
    try {
      await navigator.clipboard.writeText(button.getAttribute("data-copy"));
      button.textContent = "Copied";
      setTimeout(() => {
        button.textContent = original;
      }, 1400);
    } catch {
      button.textContent = "Select";
      setTimeout(() => {
        button.textContent = original;
      }, 1400);
    }
  });
});

const setActiveLink = (id) => {
  navLinks.forEach((link) => {
    link.classList.toggle("is-active", link.getAttribute("href") === `#${id}`);
  });
};

if ("IntersectionObserver" in window) {
  const observer = new IntersectionObserver(
    (entries) => {
      const visible = entries
        .filter((entry) => entry.isIntersecting)
        .sort((a, b) => b.intersectionRatio - a.intersectionRatio)[0];
      if (visible?.target?.id) {
        setActiveLink(visible.target.id);
      }
    },
    {
      rootMargin: "-20% 0px -65% 0px",
      threshold: [0.05, 0.2, 0.5],
    }
  );

  sections.forEach((section) => observer.observe(section));
} else if (sections[0]) {
  setActiveLink(sections[0].id);
}
