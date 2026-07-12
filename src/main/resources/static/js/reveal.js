/*
 * Entrada de secciones al scroll — fade + rise (MASTER §8).
 * Progresivo: los elementos [data-reveal] solo se ocultan si este script corre;
 * sin JS (o con prefers-reduced-motion) el contenido queda visible y estático.
 */
(function () {
  'use strict';

  var elements = document.querySelectorAll('[data-reveal]');
  if (!elements.length) return;

  var reducedMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches;
  if (reducedMotion || !('IntersectionObserver' in window)) return;

  elements.forEach(function (el) {
    var delay = el.getAttribute('data-reveal-delay');
    if (delay) el.style.transitionDelay = delay + 'ms';
    el.classList.add('na-reveal');
  });

  var observer = new IntersectionObserver(function (entries) {
    entries.forEach(function (entry) {
      if (!entry.isIntersecting) return;
      var el = entry.target;
      // el delay solo debe afectar al stagger de entrada: se limpia al terminar
      // para no contaminar transiciones futuras (p. ej. hovers) del elemento
      el.addEventListener('transitionend', function () {
        el.style.transitionDelay = '';
      }, { once: true });
      el.classList.add('is-in');
      observer.unobserve(el); // una sola vez
    });
  }, { threshold: 0.15, rootMargin: '0px 0px -40px 0px' });

  elements.forEach(function (el) {
    observer.observe(el);
  });
})();
