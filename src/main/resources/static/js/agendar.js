/*
 * /agendar: dos mejoras progresivas, ninguna es la validación real (esa
 * corre siempre en el servidor).
 * 1) Domingos: el input date nativo no puede deshabilitar días de semana
 *    puntuales, así que si el visitante elige un domingo lo limpiamos y
 *    avisamos en vez de dejarlo enviar una fecha que el servidor devolvería
 *    vacía de horarios sin explicación.
 * 2) Envío del formulario de reserva: deshabilita el botón y muestra estado
 *    de carga (MASTER §8) mientras la página navega a la confirmación.
 */
(function () {
  'use strict';

  var fechaInput = document.getElementById('fecha');
  var avisoDomingo = document.getElementById('fecha-aviso-domingo');
  if (fechaInput && avisoDomingo) {
    fechaInput.addEventListener('change', function () {
      if (!fechaInput.value) return;
      var partes = fechaInput.value.split('-').map(Number);
      var fecha = new Date(partes[0], partes[1] - 1, partes[2]);
      var esDomingo = fecha.getDay() === 0;
      avisoDomingo.hidden = !esDomingo;
      if (esDomingo) fechaInput.value = '';
    });
  }

  var form = document.querySelector('[data-reserva-form]');
  if (form) {
    form.addEventListener('submit', function () {
      var boton = form.querySelector('[data-submit-btn]');
      if (!boton || boton.disabled) return;
      var spinner = boton.querySelector('.na-btn-spinner');
      var texto = boton.querySelector('[data-submit-btn-texto]');
      boton.disabled = true;
      if (spinner) spinner.hidden = false;
      if (texto) texto.textContent = 'Confirmando…';
    });
  }
})();
