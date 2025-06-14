const tbody = document.getElementById('tabela')
let draggedRow;

tbody.addEventListener('dragstart', (e) => {
    draggedRow = e.target;
});

tbody.addEventListener('dragover', (e) => {
    e.preventDefault();

    const afterElement = getDragAfterElement(tbody, e.clientY);
    
    if (afterElement == null) {
        tbody.appendChild(draggedRow);
    } else {
        tbody.insertBefore(draggedRow, afterElement);
    }

    recalculateSequence()
});

function recalculateSequence() {
  const rows = tbody.querySelectorAll('tr');

  rows.forEach((row, index) => {
    const seqCell = row.querySelector('td:first-child');
    if (seqCell) {
      seqCell.textContent = index + 1;
    }
  });
}

function getDragAfterElement(container, y) {
    const rows = [...container.querySelectorAll('tr')];
    return rows.reduce((closest, child) => {
        const box = child.getBoundingClientRect();
        const offset = y - box.top - box.height / 2;
        if (offset < 0 && offset > closest.offset) {
            return { offset: offset, element: child };
        } else {
            return closest;
        }
    }, { offset: Number.NEGATIVE_INFINITY }).element;
}
