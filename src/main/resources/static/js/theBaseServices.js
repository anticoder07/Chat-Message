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

function handleClickItemUser(id) {
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

        })
        .catch(error => {
            console.error('Đã xảy ra lỗi: ', error);
        });
}


