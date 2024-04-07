function truncateString(str, maxLength) {
    if (str.length > maxLength) {
        return str.slice(0, maxLength) + '...';
    } else {
        return str;
    }
}

const debounce = (func, delay) => {
    let timeoutId;
    return function (...args) {
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => {
            func.apply(this, args);
        }, delay);
    };
}

function getAvatarColor(messageSender) {
    let hash = 0;
    for (let i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    let index = Math.abs(hash % colors.length);
    return colors[index];
}

function handleClickItemUser() {
    let parts = id.split("_");
    let currentId = parts[0];
    if (parts[0] !== getAuthId()) {
        currentId = parts[1];
    }

    fetch(`/api/get-user-current/${currentId}`, {
        method: 'GET',
        headers: {
            'Authorization': `bearer ${getAuthAccessToken()}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Lỗi kết nối server: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log(data)
            let avatar = document.querySelector(".chat-header .item-user .avatar-user");
            let name = document.querySelector(".chat-header .item-user .item-information .name-user");
            let des = document.querySelector(".chat-header .item-user .item-information .description");

            let avatarText = document.createTextNode(data.userName.charAt(0).toUpperCase());
            avatar.appendChild(avatarText);
            avatar.style['background-color'] = getAvatarColor(data.userName);

            name.innerText= data.userName;
            if (data.state === "work") data.state = "Đang hoạt động";
            else data.state = "Không hoạt động";
            des.innerText = data.state;
        })
        .catch(error => {
            console.error('Đã xảy ra lỗi: ', error);
        });
}

document.addEventListener("DOMContentLoaded", function () {
    handleClickItemUser();
});



