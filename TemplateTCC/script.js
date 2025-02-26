// script.js
console.log("JavaScript file is loaded!");

document.addEventListener('DOMContentLoaded', function() {
    const header = document.querySelector('header');

    window.addEventListener('scroll', () => {
        if (window.scrollY > 50) {
            header.style.backgroundColor = '#f9f9f9';
            header.style.boxShadow = '0 4px 6px rgba(0,0,0,0.1)';
        } else {
            header.style.backgroundColor = '#fff';
            header.style.boxShadow = '0 2px 5px rgba(0, 0, 0, 0.1)';
        }
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const calendarCells = document.querySelectorAll('.calendar td');
    const timeSlots = document.querySelectorAll('.time-slot');
    const selectedDateInput = document.getElementById('selected-date');
    const selectedTimeInput = document.getElementById('selected-time');

    let selectedDate = null;
    let selectedTime = null;

    calendarCells.forEach(cell => {
        cell.addEventListener('click', function () {
            if (this.textContent.trim() !== "") {
                calendarCells.forEach(c => c.classList.remove('selected'));
                this.classList.add('selected');
                selectedDate = this.textContent;
                selectedDateInput.value = selectedDate;
            }
        });
    });

    timeSlots.forEach(slot => {
        slot.addEventListener('click', function () {
            timeSlots.forEach(s => s.classList.remove('selected'));
            this.classList.add('selected');
            selectedTime = this.textContent;
            selectedTimeInput.value = selectedTime;
        });
    });
});

