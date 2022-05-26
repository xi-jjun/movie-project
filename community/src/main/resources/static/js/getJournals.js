function showJournal(journal) {
    const writer = document.querySelector("#journal-writer");
    if (!writer) {
        return;
    }
    writer.innerText = journal['writer'] + "'s Diary";

    const journalListContainer = document.querySelector("#journal-view-container");

    const journalContainer = document.createElement("div");
    journalContainer.classList.add("col");
    journalContainer.classList.add("mb-5");
    journalContainer.classList.add("d-flex");
    journalContainer.classList.add("justify-cont");

    let block = `<div class="card" style="width: 80%;">
                    <img src=${journal['imageUrl']} class="card-img-top" alt="...">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <div>
                                <h4 class="card-title">${journal['date']}</h4>
                            </div>
                            <div>
                                <h4 class="card-title">${journal['emotion']}</h4>
                            </div>
                        </div>
                        <h5 class="card-text my-2">${journal['quote']}</h5>
                        
                    </div>
                    <a class="d-flex justify-content-end mx-3 p-0" data-pk="${journal['id']}" onclick="removeJournal(this)" style="color:black; text-decoration: none;">
                        <h3>DELETE</h3>
                    </a>
                </div>`;

    journalContainer.innerHTML = block;
    journalListContainer.appendChild(journalContainer);
    // journalListContainer.innerHTML = block;
}

function removeJournal(ths) {
    const token = localStorage.getItem('Authorization');
    const id = ths.getAttribute('data-pk');
    console.log(id);

    $.ajax({
        type: 'DELETE',
        url: '/journals/' + id,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", token);
        },
        success: function (json) {
            location.reload();
        },
        error: function (json) {
        }
    });
}

$(function () {
    const token = localStorage.getItem('Authorization');
    const pathname = window.location.pathname;
    console.log(pathname);
    if (pathname !== "/view/journals") {
        console.log("getJournals.js is only operated on /view/journals");
        return;
    }

    $.ajax({
        type: 'GET',
        url: '/journals',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", token);
        },
        success: function (json) {
            for (let i = 0; i < json.length; i++) {
                showJournal(json[i]);
                console.log(json[i]);
            }
        },
        error: function (json) {
        }
    });
});

