/*
 * Count-up de cifras protagonista (MASTER §6.4b/§8.2): anima 0 → el valor
 * real una sola vez al entrar en viewport, nunca en loop. El HTML ya trae
 * el valor final como texto — sin JS (o con prefers-reduced-motion, o sin
 * IntersectionObserver) el número queda estático en ese valor desde el
 * primer render, sin parpadeo ni salto.
 */
(function () {
  'use strict';

  var elements = document.querySelectorAll('[data-countup]');
  if (!elements.length) return;

  var reducedMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches;
  if (reducedMotion || !('IntersectionObserver' in window)) return;

  var DURATION = 900;

  function animate(el) {
    var target = parseInt(el.getAttribute('data-countup'), 10);
    if (!Number.isFinite(target)) return;
    var start = null;

    function step(timestamp) {
      if (start === null) start = timestamp;
      var progress = Math.min((timestamp - start) / DURATION, 1);
      el.textContent = Math.round(progress * target);
      if (progress < 1) {
        window.requestAnimationFrame(step);
      } else {
        el.textContent = target; // asegura el valor exacto al terminar
      }
    }

    window.requestAnimationFrame(step);
  }

  var observer = new IntersectionObserver(function (entries) {
    entries.forEach(function (entry) {
      if (!entry.isIntersecting) return;
      animate(entry.target);
      observer.unobserve(entry.target); // una sola vez, nunca en loop
    });
  }, { threshold: 0.4 });

  elements.forEach(function (el) {
    observer.observe(el);
  });
})();
