fetch(`/api/get-user`, {
    method: 'GET',
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
        if (data.length > 0) {
            actionHeader.innerHTML = '';
            data.forEach(item => {
                let avatarElement = document.createElement('div');
                avatarElement.classList.add('avatar');

                let avatarText = document.createTextNode(item.userName.charAt(0).toUpperCase());
                avatarElement.appendChild(avatarText);
                avatarElement.style['background-color'] = getAvatarColor(item.userName);

                let usernameElement = document.createElement('span');
                let usernameText = document.createTextNode(truncateString(item.userName, 23));
                usernameElement.appendChild(usernameText);

                let listItem = document.createElement('li');
                listItem.setAttribute('id', item.id);
                listItem.appendChild(avatarElement);
                listItem.appendChild(usernameElement);
                listItem.addEventListener('click', function () {
                    window.location.href= `/chat-message?id=${item.id}`;
                });

                actionHeader.appendChild(listItem);
            });

        } else {
        }
    })
    .catch(error => {
        console.error('Đã xảy ra lỗi: ', error);
    });


