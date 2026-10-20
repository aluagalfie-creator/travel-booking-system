# Travel Booking System

Data Structures and Algorithms group project (PUP).
A Java Swing app for managing flights, hotels, packages, bookings, and invoices, backed by MySQL.

## Project Rules (violation = 0%)
- No external libraries. The **MySQL connector is the only dependency** in `pom.xml`.
- `setLayout(null)` on every frame and panel, with `setBounds()` on every component.
- No drag-and-drop GUI designer (no `initComponents()`, no `.form` files).
- Follow the professor's code pattern from the lessons (BE/FE).
- The database has **only the admin user** at the start.
- All other data is added through the GUI during the presentation.
- No `HashMap`, `Collections.sort`, or built-in sorting in the data structure code.

## Tech Stack
- Java JDK 24 (everyone must use the same version)
- NetBeans with Maven
- MySQL via WAMP (user `root`, empty password, port 3306)
- Java Swing

## Setup
1. Accept the GitHub invite.
2. Clone: `git clone https://github.com/aluagalfie-creator/travel-booking-system.git`
3. Open the project in NetBeans, then **Clean and Build** (internet needed).
4. Start WAMP (green icon), open phpMyAdmin, and run `database/schema.sql`.
5. Run the project and confirm the Output shows `Connected!`.

If your MySQL password differs, edit `DBConnection.java` **locally only**. Never commit it.

Default admin login: `admin` / `admin123`

## Package Structure (`src/main/java`)
| Package | Contents |
|---      |---|
| `model` | Flight, Hotel, User, Booking, PackageDeal |
| `ds`    | FlightBST, CustomerHashTable, PackageSorter, (optional) TripHeap |
| `db`    | DBConnection, FlightDB, HotelDB, UserDB, BookingDB |
| `service` | FlightCatalog, CustomerRegistry, BookingService, TravelSystem |
| `gui` | LoginFrame, RegisterFrame, MainFrame, and the panels |

## Data Structures
| Requirement | Implementation | Owner |
|---|---|---|
| Binary search tree | `FlightBST`, flights keyed by departure time | Member 2 |
| Hash table | `CustomerHashTable`, customers keyed by username (chaining) | Member 3 |
| Sorting algorithm | `PackageSorter`, package deals by price and rating | Member 4 |
| Priority queue (optional) | `TripHeap`, upcoming trips by departure | Member 5 |

## Who Owns What
| Member | Name | Files |
|---|---|---|
| 1 (Leader) | Alfie | `model/*`, `DBConnection`, `TravelSystem`, `MainFrame`, `LoginFrame`, README |
| 2 | Alyanna | `FlightBST`, `FlightDB`, `FlightCatalog`, `FlightPanel` |
| 3 | Cedie | `CustomerHashTable`, `UserDB`, `CustomerRegistry`, `RegisterFrame`, `CustomerPanel` |
| 4 | Rhyjel | `PackageSorter`, `PackageDeal`, `HotelDB`, `HotelPanel`, `PackagePanel` |
| 5 | Cristofer | `BookingDB`, `BookingService`, `BookingPanel`, `HistoryPanel`, `InvoiceFrame`, `ReportPanel`, `TripHeap` |

**Only edit your own files.** If you need a change in someone else's file, message the owner.

## Method Contracts
Code against these names so everything fits together. If you change one, update this list and tell the group.

```
FlightCatalog
  addFlight(Flight f)
  updateFlight(Flight f)
  deleteFlight(int flightId)
  searchByDestination(String destination)
  getAllSortedByDeparture()
  getById(int flightId)

CustomerRegistry
  register(User u)
  getByUsername(String username)
  update(User u)
  delete(int userId)
  getAll()

HotelDB
  addHotel(Hotel h)
  updateHotel(Hotel h)
  deleteHotel(int hotelId)
  getAllHotels()
  getById(int hotelId)

PackageSorter
  sortByPrice(PackageDeal[] deals, boolean ascending)
  sortByRating(PackageDeal[] deals)

BookingService
  book(int userId, int flightId, int hotelId)   // use 0 for none
  cancel(int bookingId)
  getHistory(int userId)
  getAllBookings()
```

## Shared Conventions
- Dates are `String` in the format `yyyy-MM-dd HH:mm` (for example `2026-11-05 14:30`).
- A booking with no flight or no hotel uses `0` for that ID (the DB stores `NULL`).
- Roles are `ADMIN` or `CUSTOMER`.
- Every GUI action validates its input and shows a `JOptionPane` message on errors (empty fields, duplicate username, negative price, DB failure).
- On startup, `TravelSystem.loadAll()` reads MySQL into the data structures. Every add, update, or delete changes **both** the structure and MySQL.

## Git Workflow
Before you start working:
```
git pull origin main
```
After finishing a piece of work:
```
git add .
git commit -m "Describe what you did"
git pull origin main
git push origin main
```
- Commit small and often, with clear messages (`Add FlightBST insert`).
- Never commit `target/` or a local DB password change.
- Run **Clean and Build** after pulling. If the build breaks, tell the group.

## Demo Flow (rehearse from an empty database)
1. Log in as admin, then add flights, hotels, and packages.
2. Log out, register a customer, and log in.
3. Search flights and hotels, then build a package and sort it by price.
4. Book it, then view the itinerary and the invoice.
5. Log in as
