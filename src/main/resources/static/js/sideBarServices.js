function handleSideBarService() {
    fetch(`/api/get-user`, {
        method: 'GET',
        headers: {
            'Authorization': `bearer ${getAuthAccessToken()}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Lỗi kết nối server");
            }
            return response.json();
        })
        .then(data => {
            console.log(data)
            let checkIcloud = false;
            if (data.length > 0) {
                actionHeader.innerHTML = '';
                data.forEach(item => {
                    let avatarElement = document.createElement('div');
                    avatarElement.classList.add('avatar');

                    let avatarText = document.createTextNode(item.userName.charAt(0).toUpperCase());
                    avatarElement.appendChild(avatarText);
                    avatarElement.style['background-color'] = getAvatarColor(item.userName);

                    let usernameElement = document.createElement('span');
                    let userNameItem = Number(item.id) === Number(getAuthId()) ? "Icloud của tôi" : item.userName;
                    let tempCheck = Number(item.id) === Number(getAuthId());
                    let usernameText = document.createTextNode(truncateString(userNameItem, 23));
                    usernameElement.appendChild(usernameText);

                    let listItem = document.createElement('li');
                    listItem.setAttribute('id', "user-" + item.id);
                    listItem.appendChild(avatarElement);
                    listItem.appendChild(usernameElement);
                    listItem.addEventListener('click', function () {
                        let duoRoomId = `${getAuthId()}_${item.id}`;
                        if (getAuthId() > item.id) {
                            duoRoomId = `${item.id}_${getAuthId()}`;
                        }
                        window.location.href = `/chat-message?id=${duoRoomId}`;
                    });

                    if (item.notification) {
                        listItem.classList.add("red-notification");
                    }

                    if (!checkIcloud || checkIcloud !== tempCheck){
                        if (!checkIcloud) {
                            actionHeader.appendChild(listItem);
                        } else {
                            actionHeader.insertBefore(listItem, actionHeader.firstElementChild);
                        }
                    }
                    if (tempCheck) {
                        checkIcloud = tempCheck;
                    }
                });
            }
        })
        .catch(error => {
            console.error('Đã xảy ra lỗi: ', error);
        });

}

handleSideBarService();




