# Сценарии использования

В данном разделе описываются сценарии использования приложения, а также демонстрируется его графический интерфейс.

- [1 - Вход](#1-вход)
- [2 - Регистрация](#2-регистрация)
- [3 - Выход](#3-выход)
- [4 - Каталог](#4-каталог)
- [5 - Карта товара](#5-карточка-товара)
- [6 - Добавление в корзину](#6-добавление-в-корзину)
- [7 - Корзина](#7-корзина)
- [8 - История заказов](#8-история-заказов)
- [9 - Админ панель](#9-админ-панель)
- [9.1 - Панель товаров](#91-панель-товаров)
- [9.2 - Панель категорий](#92-панель-кategorий)
- [9.3 - Панель заказов](#93-панель-заказов)
- [9.4 - Панель пользователей](#94-панель-пользователей)

<a name='вход'></a>
## 1. Вход

Для входа на сайт необходимо указать действительные данные: логин (номер телефона или адрес электронной почты) и пароль.

Окно входа на сайт:
![image_2025-02-27_16-31-09](https://github.com/user-attachments/assets/221cc36a-a434-48f0-8251-fd0184f72117)

Успешный вход:
![image_2025-02-27_16-31-25](https://github.com/user-attachments/assets/b2097b81-0e80-49b2-ae0f-d41a0d513383)

Неуспешный вход:
![image_2025-02-27_16-35-59](https://github.com/user-attachments/assets/326caa90-f0b4-41aa-97f6-624796c687a1)

<a name='регистрация'></a>
## 2. Регистрация

Для регистрации необходимо заполнить форму. Присутствует валидация.

Окно регистрации на сайте:
![image](https://github.com/user-attachments/assets/39a16072-ad1e-45b3-b1d5-1e4eeee8806f)

Сообщение об ошибке валидации:
![image](https://github.com/user-attachments/assets/e2c2c6ce-f2ec-4d19-b46c-a9e1d6bf4c6b)

Сообщение об успешной регистрации:
![image](https://github.com/user-attachments/assets/3a176517-e4a2-4cca-a16f-ef82f6175e2f)

<a name='выход'></a>
## 3. Выход

Окно выхода из учетной записи на сайте:
![image_2025-02-27_16-34-43](https://github.com/user-attachments/assets/cce59037-4b69-495a-93b7-62b7db9768a4)

<a name='каталог'></a>
## 4. Каталог

Каталог доступен всем пользователям (админам, кастомерам и незарегистрированным). Внешний вид каталога товаров:
![image_2025-02-27_16-31-43](https://github.com/user-attachments/assets/3d56c57e-05b0-41d7-b6f8-68c821f1f1da)

Внизу каталога присутствует пагинация. Вверху доступна фильтрация по категориям и сортировка по полям с выбором порядка сортировки.

Пример фильтрации и сортировки в каталоге:
![image](https://github.com/user-attachments/assets/b3347136-7bfe-43a0-bf1b-d9fc51979752)

<a name='карточка-товара'></a>
## 5. Карточка товара

В карточке товара отображается информация о товаре, включая описание. Внешний вид карточки товара:
![image](https://github.com/user-attachments/assets/f246af6a-90ac-4f5d-b64d-f8dc43fcaf09)

<a name='добавление-в-корзину'></a>
## 6. Добавление в корзину

В карточке товара необходимо нажать кнопку для добавления товара в корзину, после чего появится следующее сообщение:
![image](https://github.com/user-attachments/assets/954303d1-fcff-4740-b076-aebe5286731e)

<a name='корзина'></a>
## 7. Корзина

В корзине можно управлять количеством добавленных в неё товаров, а также удалять товары из неё. Там же можно оформить заказ на основе корзины. Внешний вид корзины:
![image](https://github.com/user-attachments/assets/ee5f931c-fbdc-4c4c-9547-14bdb634e4e8)

Если корзина пуста, будет выведено соответствующее сообщение:
![image_2025-02-27_16-36-32](https://github.com/user-attachments/assets/8eaf4eef-3713-497f-a68a-6cfa1b617003)

<a name='история-заказов'></a>
## 8. История заказов

Пользователь может посмотреть историю своих заказов. Доступна фильтрация и сортировка.
![image](https://github.com/user-attachments/assets/7c910340-00f6-4664-9964-d902f6d143e5)

<a name='админ-панель'></a>
## 9. Админ панель

При входе на сайт можно указать данные для входа администратора, после чего будет доступна служебная панель.

<a name='панель-товаров'></a>
### 9.1 Панель товаров

В панели товаров отображаются все существующие товары. Их можно редактировать, а также добавлять новые. Удалять товары нельзя по причине их использования в истории заказов. Присутствует пагинация, сортировка и фильтрация.
![image](https://github.com/user-attachments/assets/dfa8ef5d-23e1-448b-85bb-00ba5f855c5e)

Форма изменения данных о товаре:
![image](https://github.com/user-attachments/assets/7e41ab53-8300-4d71-a936-9b0a4b4147be)

<a name='панель-кategorий'></a>
### 9.2 Панель категорий

Панель категорий аналогична панели товаров. Можно также удалять категории товаров. Все связанные продукты будут откреплены от удаленной категории.
![image](https://github.com/user-attachments/assets/45d7e497-b1e0-40c9-90b3-599f2e4a109d)

Форма для добавления новой категории:
![image](https://github.com/user-attachments/assets/92ae8898-966a-4932-93ec-3cd959238ccb)

<a name='панель-заказов'></a>
### 9.3 Панель заказов

В панели заказов можно просматривать все заказы в приложении, а также фильтровать и сортировать их по статусу, дате и т.п. Также можно изменять статус заказа, тем самым управляя его жизненным циклом.
![image](https://github.com/user-attachments/assets/933ac5f0-8747-4c7a-9e46-6dd3833715c5)

<a name='панель-пользователей'></a>
### 9.4 Панель пользователей

Панель пользователей позволяет администратору блокировать/разблокировать пользователей системы, а также связываться с ними по номеру телефона или электронной почте.
![image](https://github.com/user-attachments/assets/7b79a261-13f7-48c8-a6e9-b91dfacadc0f)
