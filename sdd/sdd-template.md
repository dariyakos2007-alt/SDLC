# Описание дизайна программного обеспечения для веб-приложения «Puff & Plate»

## 1. Введение

### 1.1 Назначение документа

Настоящий документ (SDD) описывает архитектуру и дизайн программного обеспечения для веб-приложения «Puff & Plate». Документ предназначен для команды разработки, тестировщиков и преподавателя.

### 1.2 Область применения

**«Puff & Plate» 0.1** — веб-приложение для организации кейтеринга. Система позволяет клиентам выбирать готовые сеты или собирать индивидуальное меню, рассчитывать стоимость с учетом доставки (и порога бесплатной доставки) и оформлять заказ.

### 1.3 Определения, акронимы и сокращения

| Термин | Определение |
|------|------------|
| API | Application Programming Interface — программный интерфейс |
| SRS | Software Requirements Specification — спецификация требований |
| SDD | Software Design Document — документ описания дизайна |
| SPA | Single Page Application — одностраничное приложение (React) |

### 1.4 Ссылки

* **SRS:** [Ссылка на srs-template.md](../srs/srs-template.md)
* **ADR:** [Ссылка на adr-template.md](../adr/adr-template.md)

---

## 2. Обзор дизайна

### 2.1 Задачи стейкхолдеров

* **Заказчик:** Получить работающее MVP, которое автоматизирует расчет заказов.
* **Разработчик:** Использовать понятную модульную архитектуру.
* **Тестировщик:** Иметь четкие интерфейсы для написания тестов.

### 2.2 Выбранные точки зрения

#### 2.2.1 Контекст

Система «Puff & Plate» является «черным ящиком» для клиента. 
* **Акторы:** Клиент (браузер), База данных (PostgreSQL).
* **Внешние системы:** Отсутствуют.

#### 2.2.2 Композиция

Система разделена на три логических компонента:
1. **Frontend (React SPA):** Отображение UI, отправка запросов к API.
2. **Backend (Spring Boot):** Бизнес-логика (включая расчет стоимости и бесплатной доставки), обработка запросов.
3. **Database (PostgreSQL):** Хранение данных о блюдах, сетах, пользователях и заказах.

#### 2.2.3 Логическое представление

* **Слой контроллеров (Controllers):** Принимают HTTP-запросы.
* **Слой сервисов (Services):** Содержат бизнес-логику (расчет стоимости, проверка порога доставки).
* **Слой репозиториев (Repositories):** Доступ к данным через Spring Data JPA.

#### 2.2.7 Информация

Основные сущности базы данных:
* **User:** `id`, `email`, `password_hash`, `role`, `phone_number`.
* **CateringSet:** `id`, `name`, `persons_count`, `price`, `ingredients`.
* **Dish:** `id`, `name`, `price`, `description`, `ingredients`.
* **Order:** `id`, `user_id`, `delivery_address`, `delivery_date`, `total_items_price`, `delivery_cost`, `total_price`, `status`.

#### 2.2.14 Развёртывание

Система разворачивается в Docker-контейнерах:
1. `frontend` (Nginx + React build).
2. `backend` (Java JAR).
3. `db` (PostgreSQL).

---

## 3. Проектные представления

* **ID:** 001-API
* **Title:** REST API для взаимодействия Frontend и Backend
* **Viewpoint:** Интерфейс (Interface)
* **Representation:** Все запросы идут в формате JSON. Основные эндпоинты: `GET /api/dishes`, `GET /api/sets`, `POST /api/orders`.
* **More Information:** Соответствует REQ-INT-001 и REQ-INT-002.

* **ID:** 002-DB
* **Title:** Схема базы данных
* **Viewpoint:** Информация (Information)
* **Representation:** Таблицы создаются через Hibernate. Связи: Один пользователь — много заказов (1:N).

* **ID:** 003-CALC
* **Title:** Логика расчета стоимости заказа
* **Viewpoint:** Алгоритм (Algorithm)
* **Representation:** 
  1. Система суммирует стоимость всех позиций в корзине (`total_items_price`).
  2. Система проверяет: если `total_items_price` >= 200 BYN, то `delivery_cost` = 0.
  3. Иначе `delivery_cost` = 15 BYN (фиксированная ставка).
  4. Итоговая стоимость = `total_items_price` + `delivery_cost`.

---

## 4. Решения

* **ID:** 001-ARCH
* **Title:** Выбор стека технологий
* **Context:** Необходимо выбрать язык и фреймворки для реализации.
* **Options:** Java/Spring Boot, Python/Django, Node.js/Express.
* **Outcome:** Выбран Java/Spring Boot, так как обеспечивает строгую типизацию и безопасность.
* **More Information:** См. файл `adr/adr-template.md`.

---

## 5. Приложения

* **Репозиторий проекта:** [https://github.com/dariyakos2007-alt/SDLC/tree/lab3-requirements](https://github.com/dariyakos2007-alt/SDLC/tree/lab3-requirements)
* **Структурная диаграмма главной страницы (PNG):** [dashboard.png](https://github.com/dariyakos2007-alt/SDLC/blob/lab3-requirements/srs/images/dashboard.png)
* **Структурная диаграмма «Конструктор заказа» (PNG):** [constructor.png](https://github.com/dariyakos2007-alt/SDLC/blob/lab3-requirements/srs/images/constructor.png)
