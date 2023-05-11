let images = [5];

function submit() {
    let title = getTitle();
    let description = getDescription();
    let price = getPrice();
    let discount = getDiscount();
    let characteristic = getCharacteristic();
    let quantityInStock = getQuantityInStock();
    let category = getCategory();
    let image = getImages();


    let product = {
        title: title,
        categoryId: category,
        quantityInStock: quantityInStock,
        description: description,
        price: price,
        discount: discount,
        images: image,
        characteristics: Object.fromEntries(characteristic),
    }
    alert("fast")
    $.ajax({
        url: "/products/admin",
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(product),
        contentType: 'application/json',
        mimeType: 'application/json',

    }).done(function () {
        window.location = "http://localhost:8083/admin"
    })
        .fail(function () {
            window.location = "http://localhost:8083/security/authorization_check"
        });
}

function getCategory() {
    return document.getElementById("select").value;
}

function getImages() {
    let image = "";
    for (let i = 0; i < images.length; i++) {
        image += (images[i] + " ");
    }
    return image;
}


function imagesClick(element, number) {
    let file = element.files[0];
    let reader = new FileReader();
    reader.onload = function () {
        images[number] = (reader.result.replace("data:", "")
            .replace(/^.+,/, "")).trim();
        alert(images[number])
    }
    reader.readAsDataURL(file);
}

function getTitle() {
    return document.getElementById('title').value.trim();
}

function getDescription() {
    return document.getElementById('description').value.trim();
}

function getPrice() {
    return document.getElementById('price').value.trim();
}

function getDiscount() {
    return document.getElementById('discount').value.trim();
}

function getQuantityInStock() {
    return document.getElementById('quantityInStock').value.trim();
}

function getCharacteristic() {
    let map = new Map();
    let children = document.getElementById('characteristic').children;
    for (let i = 0; i < children.length; i++) {
        alert(children[i].children[0].value.trim());
        if (children[i].children[0].value.trim().length !== 0 && children[i].children[2].value.trim().length !== 0) {
            map.set(children[i].children[0].value.trim(), children[i].children[2].value.trim())
        }
    }
    return map;
}

    let countOfFields = 1;
    let curFieldNameId = 1;
    let maxFieldLimit = 10;

    function deleteField(a) {
    if (countOfFields > 1) {
    let contDiv = a.parentNode;
    alert(contDiv.id)
    contDiv.parentNode.removeChild(contDiv);
    countOfFields--;
}
    return false;
}

    function addField() {
    if (countOfFields >= maxFieldLimit) {
    alert("Число полей достигло своего максимума = " + maxFieldLimit);
    return false;
}
    countOfFields++;
    curFieldNameId++;
    let div = document.createElement("div");
    div.innerHTML = "<input type=\"text\"\n" +
    "                       style=\"border: black 1px solid;font-size: 15px;border-radius: 5px;width:300px;height: 30px\"/>\n" +
    "                <a style=\"color:red;\" onclick=\"return deleteField(this)\" href=\"#\">[—]</a>\n" +
    "                <input maxlength=\"10\" type=\"text\"\n" +
    "                       style=\"border: black 1px solid;font-size: 15px;width:300px;border-radius: 5px;height: 30px\"/>\n" +
    "                <a style=\"color:green;\" onclick=\"return addField()\" href=\"#\">[+]</a>"
    // Добавляем новый узел в конец списка полей
    document.getElementById("characteristic").appendChild(div);
    return false;
}
