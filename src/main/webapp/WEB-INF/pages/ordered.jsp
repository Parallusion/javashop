<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style type="text/css">
        body {
            background-color: aquamarine;
            color: #000000;
            font-family: Impact;
            font-weight: 100;
            height: 100vh;
            margin: 0;
            -webkit-background-size:cover;
            padding: 0;

        }

        table {
            border-spacing: 0 10px;
            font:  bold 100% Georgia, serif;
            margin: 40px auto;
            text-shadow: 5px 5px 5px #3F3F7F;
            background: #B1B9D9;
            text-align: center;
            vertical-align: middle;
            width: 50%;
            border: 10px solid blue;
        }

        p {
            margin: 0;
        }
        footer
        {
            background-color: #1B1E8A;
        }

        a {
            color: #118BF5;
            text-decoration: none;
        }
        /* -----  google Font  -----*/
        @import url('https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700');

        /* -----  Р’С‹СЂР°РІРЅРёРІР°РЅРёРµ РїРѕ СЃРµСЂРµРґРёРЅРµ  -----*/
        .dws-wrapper {
            display: flex;
            height: 100vh;
            justify-content: center;
            align-items: center;
        }
        .dws-form {
            color: #000000;
            text-shadow: 0px 4px 4px #6E70C4;
            padding: 10px;
            width: 330px;
        }
        .button{
            background-color: black;
            color:#6E70C4;
        }
        .dws-form a {

            text-decoration: none;
            transition: 0.5s;
            color: #298cad;
        }
        .dws-form a:hover {
            color: #36d2ff;
        }
        .dws-form ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        .dws-form input {
            display: block;
            width: 100%;
        }
        .tab-form {
            background-color: #C6750B;
            display:none;
            padding: 30px 20px 20px;
            opacity: 0.95;
        }

        .tab-form.active
        {
            display:block;
        }

        label.tab {
            color:black;
            font-weight: 600;
            padding: 15px;
            display: inline-block;
            border-radius: 3px 3px 0 0;
            margin-bottom: -1px;
            cursor:pointer;
        }

        label.tab.active {
            background-color: #C6750B;
            color: black;
            width: 40%;
        }

        label.tab {
            border-radius: 3px 3px 0 0;
            margin-bottom: -1px;
            cursor: pointer;
            opacity: 0.95;
            width: 40%;
            background-color: darkorange;
        }

        .del {
            display: none;
        }
        .input {
            font-size: 16px;
            background-color: #6E70C4;
            border: 2px solid #6E70C4;
            padding: 12px 14px;
            box-sizing: border-box;
            margin-bottom: 22px;
            color: black;
            outline: none;
        }

        a.button {
            height: 40px;
            width:80px;
            margin:auto;
            background: #2b542c;
            color: black;
            font-size: 11px;
            text-transform: uppercase;
            display: block;
            text-align: center;
            line-height: 46px;
            margin-top: 30px;
            margin-bottom: 10px;
            position:relative;
            z-index: 1;
            overflow: hidden;
            border-bottom: 4px solid #6E70C4;
        }
        a.button:hover {
            color: black;
        }
        .button:hover::before {
            left: 0;
        }
        .button::after {
            transition-delay: .2s;
        }
        .button:hover::after {
            top: 0;
        }

        .btn
        {
            background-color: #1B1E8A;
        }

        /* РџР»Р°РІР°СЋС‰РёР№ С‚РµРєСЃС‚ */
        .box-input {
            position: relative;
        }

        .box-input input {
            border: none;
            border-bottom: 2px solid #6E70C4;
            padding: 10px 0;
        }

        .box-input label {
            position: absolute;
            top: 0;
            left: 0;
            padding-top: 10px;
            color: black;
            transition: .5s;
            pointer-events: none;
        }
        .box-input input:focus ~ label,
        .box-input input:valid ~ label {
            top: -25px;
            font-size: 12px;
            color: black;
        }
        input:invalid {

        }

        input:valid {

        }
        #knop{
            margin-left:auto;
            margin-right: auto;
            background-color: #1B1E8A;
            color:#6E70C4;
            border: 2px solid #6E70C4;
        }
        /* Р­С„С„РµРєС‚ РєРЅРѕРїРєРё */
        .button::before, .button::after {
            content: '';
            display: block;
            width: 100%;
            height: 100%;
            background: #28a5c4;
            position: absolute;
            top: 0;
            left: -100%;
            transition: .3s;
            z-index: -1;

        }
        .button::after {
            background: #36d2ff;
            top: 100%;
            left: 0;
        }

        /* РРєРѕРЅРєРё РѕС‚ Font Awesome */
        .tab::before {
            font-family: 'fontawesome', sans-serif;
            font-size: 24px;
            margin-right: 10px;
            font-weight: normal;
        }
        .tab[for*="2"]::before {
        }
        #captcha{
            background-color: #6E70C4;
        }
    </style>
    <title>Заказы</title>
</head>
<body>
    <a href="/things">Товары</a>
    <a href="/ordered">Заказы</a>
    <a href="/basket">Корзина</a>
    <c:if test="${empty buyer.name}">
        <a href="/addProfile">Профиль</a>
    </c:if>
    <c:if test="${!empty buyer.name}">
        <a href="/profile">Профиль</a>
    </c:if>

    <h2>Все заказы</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Почта</th>
            <th>Адрес доставки</th>
            <th>Товары</th>
            <th>Количество</th>
            <th>Сумма</th>
            <th>Телефон</th>
        </tr>
        <c:forEach var="ordered" items="${ordered}">
            <tr>
                <td>${ordered.id} </td>
                <td>${ordered.name}</td>
                <td>${ordered.email}</td>
                <td>${ordered.delivery}</td>
                <td>${ordered.things}</td>
                <td>${ordered.number}</td>
                <td>${ordered.sum}</td>
                <td>${ordered.phone}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
