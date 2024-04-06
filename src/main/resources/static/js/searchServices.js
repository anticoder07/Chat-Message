const handleSearch = debounce((event) => {
    let params = event.target.value;
    fetch(`/api/search?s=${params.toString()}`, {
        method: 'POST',
        headers: {
            'Authorization': `bearer ${getAuthAccessToken()}`
        }
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Lỗi kết nối server");
            }
        })
        .then(data => {
            searchResults.innerHTML = '';
            if (data.length > 0) {
                searchResultsItem = data;
                searchResults.innerHTML = '';
                searchResultsContainer?.classList.remove("hidden");
                data.forEach(item => {
                    let avatarLetter = item.userName.charAt(0).toUpperCase();

                    let avatarElement = document.createElement('div');
                    avatarElement.classList.add('avatar');

                    let avatarText = document.createTextNode(avatarLetter);
                    avatarElement.appendChild(avatarText);
                    avatarElement.style['background-color'] = getAvatarColor(avatarLetter);

                    let usernameElement = document.createElement('span');
                    let usernameText = document.createTextNode(truncateString(item.userEmail, 23));
                    usernameElement.appendChild(usernameText);

                    let listItem = document.createElement('li');
                    listItem.setAttribute('id', item.id);
                    listItem.appendChild(avatarElement);
                    listItem.appendChild(usernameElement);
                    listItem.addEventListener('mousedown', function(event) {
                        event.stopPropagation();
                        window.location.href= `/chat-message?id=${item.id}`;
                    });

                    searchResults.appendChild(listItem);
                });

            } else {
                let listItem = document.createElement('li');
                listItem.textContent = "Không tìm thấy kết quả";
                searchResults.appendChild(listItem);
            }
        })
        .catch(error => {
            console.error('Đã xảy ra lỗi: ', error);
        });
}, searchDelayTime);

search.addEventListener("blur", function () {
    searchResults.classList.add("hidden");
});
search.addEventListener("focus", function () {
    searchResults.classList.remove("hidden");
});