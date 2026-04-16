# Car Showroom Management System

## Overview

**Car Showroom Management System** is a console-based inventory management application designed for automotive dealerships. It enables showroom staff to efficiently manage vehicle brands and their corresponding inventory with comprehensive CRUD operations, advanced search capabilities, data validation, and persistent storage.

The application solves the problem of maintaining organized records for:
- Multiple car brands with attributes like sound systems and pricing
- Individual vehicle inventory linked to brands
- Quick searches and filtering by various criteria
- Data integrity through validation and referential constraints

This is ideal for small to medium-sized car dealerships that need a lightweight desktop solution without the overhead of a database.

---

## Features

1. **List all brands** - Display complete brand inventory in a formatted table with ID, name, sound system, and price
2. **Add a new brand** - Create new brand records with validation (unique ID, required fields)
3. **Search for a brand by ID** - Quickly locate a specific brand using exact ID matching
4. **Update a brand by ID** - Modify existing brand information (name, sound system, price)
5. **Search brands by price** - Filter all brands with pricing less than or equal to a specified threshold
6. **List cars by brand (ascending)** - View all vehicles sorted by brand name, then by price descending
7. **Search cars by brand name** - Find vehicles matching a partial brand name (substring search)
8. **Add a new car** - Create vehicle records with validation (unique ID, valid brand reference, proper frame/engine ID formats, unique frame/engine IDs)
9. **Remove a car by ID** - Delete vehicles from inventory
10. **Update a car by ID** - Modify existing vehicle information (color, frame ID, engine ID)
11. **Search cars by color** - Filter vehicle inventory by specific color
12. **Save data to files** - Persist all changes to CSV data files
13. **Quit program** - Exit with unsaved change detection and optional data persistence

---

## Tech Stack

- **Language**: Java 8+
- **Architecture**: Model-View-Controller (MVC)
- **Build System**: Apache Ant
- **Data Storage**: CSV text files (no external database)
- **IDE**: NetBeans (project configured via nbproject/)
- **File I/O**: Java BufferedReader/BufferedWriter

---

## Project Structure

```
CarShowroomManagement/
├── src/
│   ├── model/                 # Data models
│   │   ├── Brand.java        # Brand entity (id, name, soundBrand, price)
│   │   └── Car.java          # Car entity (id, brandID, color, frameID, engineID)
│   ├── controller/           # Business logic & data operations
│   │   ├── BrandService.java      # Brand CRUD, import, search, save
│   │   ├── CarService.java        # Car CRUD, import, search, sort, save
│   │   ├── ImportData.java        # CSV file reading utility
│   │   └── InputValidator.java    # Input validation (ID formats, prices, text)
│   ├── view/
│   │   └── Main.java         # Console UI with interactive menu (13 operations)
│   └── data/                 # Data files
│       ├── brands.txt        # Brand inventory (CSV format)
│       └── cars.txt          # Car inventory (CSV format)
├── build.xml                 # Ant build configuration
├── nbproject/                # NetBeans project metadata
└── manifest.mf              # Java manifest file
```

### Architecture Pattern: MVC

- **Model** (`model/`): Pure data objects representing domains
  - `Brand`: Encapsulates brand properties with serialization methods
  - `Car`: Encapsulates vehicle properties with serialization methods

- **Controller** (`controller/`): Business logic and state management
  - `BrandService`: Manages brand operations (add, search, update, save), tracks changes
  - `CarService`: Manages car operations (add, search, update, save), validates brand references
  - `InputValidator`: Centralized validation for all user input
  - `ImportData`: File I/O operations for loading CSV data

- **View** (`view/`): User interaction layer
  - `Main`: Interactive console menu with 13 operations, change tracking, data persistence prompts

---

## How It Works

### Data Flow Overview

```
Startup
  ↓
Load brands from src/data/brands.txt (BrandService.importDataBrand)
  ↓
Load cars from src/data/cars.txt (CarService.importDataCar)
  ↓
Display Main Menu
  ↓
User selects operation (1-13)
  ↓
Perform operation with validation
  ↓
Update in-memory collections (sets CHANGE flag)
  ↓
Return to menu (loop until exit)
  ↓
On exit: check CHANGE flags → prompt to save if modified
  ↓
Save to CSV files (if user chooses) → Exit
```

### Key Operations

**Brand Operations**:
1. `BrandService.importDataBrand()` - Load brands with validation (rejects duplicates, invalid prices, missing fields)
2. `BrandService.displayAll()` - Format and print all brands in table view
3. `BrandService.addBrand()` - Interactive input collection for new brand with unique ID check
4. `BrandService.searchBrandByID()` - Linear search, case-insensitive matching
5. `BrandService.updateBrandByID()` - Find and modify brand properties
6. `BrandService.searchBrandByPrice()` - Filter brands by maximum price threshold
7. `BrandService.saveData()` - Write all brands to CSV with proper formatting

**Car Operations**:
1. `CarService.importDataCar()` - Load cars with validation (checks brand existence, Frame/Engine ID uniqueness and format)
2. `CarService.ascendingBrand()` - Sort cars by brand name (ascending) then price (descending)
3. `CarService.searchCarByBrand()` - Substring search on brand names
4. `CarService.addCar()` - Interactive input with multiple validations
5. `CarService.removeCar()` - Delete car by ID
6. `CarService.updateCarByID()` - Modify car properties
7. `CarService.searchCarByColor()` - Filter by exact color match
8. `CarService.saveData()` - Write all cars to CSV with proper formatting

### Data Validation Pipeline

**Brand Validation** (on import & add):
- ID: Non-empty, unique, case-normalized to uppercase
- Name: Non-empty required
- Sound System: Non-empty required, must include colon separator (format: `SoundBrand : Price`)
- Price: Positive decimal with "B" suffix (e.g., `3.749B` = 3.749 billion VND)

**Car Validation** (on import & add):
- ID: Non-empty, unique, case-normalized to uppercase
- BrandID: Non-empty, must exist in loaded brands (referential integrity)
- Color: Non-empty, text-only (letters and spaces), case-sensitive
- FrameID: Exactly 6 characters, format `F#####` (starts with F, followed by 5 digits)
- EngineID: Exactly 6 characters, format `E#####` (starts with E, followed by 5 digits)
- Uniqueness: Frame IDs and Engine IDs must be unique across all cars

**User Input Validation**:
- `InputValidator.isPositiveDouble()` - Checks for positive decimal numbers
- `InputValidator.isPositiveInteger()` - Checks for non-negative integers
- `InputValidator.getDoublePrice()` - Extracts numeric value from price strings (removes "B" suffix)
- `InputValidator.isValidText()` - Ensures text contains only letters and spaces
- `InputValidator.isFrameID()` - Validates Frame ID format (F + 5 digits)
- `InputValidator.isEngineID()` - Validates Engine ID format (E + 5 digits)

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)** 8 or later installed on your system
- **Apache Ant** for building the project
- **Command-line terminal** (PowerShell, Command Prompt, or Bash)

### Installation Steps

1. **Clone or download the repository**:
   ```bash
   cd CarShowroomManagement
   ```

2. **Verify project structure**:
   ```bash
   dir  # Windows
   ls   # Linux/Mac
   ```
   Ensure `build.xml`, `src/`, and `nbproject/` directories exist.

3. **Build the project with Ant**:
   ```bash
   ant clean build
   ```
   This compiles Java source files and outputs classes to `build/classes/`.

### Running the Application

After a successful build, run the application:

```bash
java -cp build/classes view.Main
```

The interactive menu will appear in your console.

---

## Usage

### Interactive Menu Options

When you launch the application, you'll see the main menu:

```
===== CAR SHOWROOM MANAGEMENT =====
1. List all brands
2. Add a new brand
3. Search for a brand by ID
4. Update a brand by ID
5. List all brands with prices less than or equal to an input value
6. List all cars in ascending order of brand names
7. Search cars by partial brand name match
8. Add a new car
9. Remove a car by ID
10. Update a car by ID
11. List all cars by a specific color
12. Save data to files
13. Quit program

Enter your choice:
```

### Example Workflows

#### Example 1: View All Brands
```
Enter your choice: 1

List all brands:
---------------------------------------------------------------------------------------
Brand ID   | Brand Name                               | Sound Brand     | Price
---------------------------------------------------------------------------------------
B7-2018    | BMW 730Li (2018)                         | Harman Kardon   | 3.749B
B7-MS      | BMW 730Li M Sport                        | Harman Kardon   | 4.319B
B5-18      | BMW 530i (2018)                          | Alpine          | 2.599B
---------------------------------------------------------------------------------------
```

#### Example 2: Add a New Brand
```
Enter your choice: 2

==Add New Brand ==
Enter Brand ID (Enter CANCEL to return menu): BMW-X5-21
Enter Brand name (Enter CANCEL to return menu): BMW X5 xDrive50i
Enter sound manufacturers (Enter CANCEL to return menu): Harman Kardon
Enter price (Ex: 1.234 means 1.234 billion(s)$): 5.899

Add a new brand: BMW-X5-21 success!
```

**Note**: Price input should be a positive number without the "B" suffix (e.g., `5.899` not `5.899B`). The system automatically appends "B".

#### Example 3: Search for a Brand by ID
```
Enter your choice: 3

== Search Brand By ID==
Enter ID Brand to search: B7-2018

== Matching Brands ==
---------------------------------------------------------------------------------------
Brand ID   | Brand Name                               | Sound Brand     | Price
---------------------------------------------------------------------------------------
B7-2018    | BMW 730Li (2018)                         | Harman Kardon   | 3.749B
---------------------------------------------------------------------------------------
```

#### Example 4: Add a New Car
```
Enter your choice: 8

== Add New Car ==
Enter Car ID (Enter CANCEL to return menu): C10

List of brands to choose:
1 - B7-2018 - BMW 730Li (2018) - Harman Kardon - 3.749B
2 - B7-MS - BMW 730Li M Sport - Harman Kardon - 4.319B
3 - B5-18 - BMW 530i (2018) - Alpine - 2.599B

Enter Brand ID (Enter CANCEL to return menu): 1

Enter color (Enter CANCEL to return menu): silver
Enter FrameID (Ex: F00000): F12352
Enter EngineID (Ex: E00000): E12352

Add a new car: C10 success!
```

**Important**: Frame IDs and Engine IDs must follow the exact format with leading letter and 5 digits.

#### Example 5: Search Cars by Color
```
Enter your choice: 11

== Search cars by color==
Enter color to search: red

== Matching Cars ==
-----------------------------------------------------
Car ID     | Brand ID        | Brand Name            | Color     | FrameID    | EngineID
-----------------------------------------------------
C01        | B7-2018         | BMW 730Li (2018)      | red       | F12345     | E12345
-----------------------------------------------------
```

#### Example 6: Save and Exit
```
Enter your choice: 12

Save data to files: Success!

Enter your choice: 13

You have unsaved changes. Do you want to save the changes before exiting? (Y/N): Y

Exited program!
```

---

## Configuration

### Data File Locations

The application expects data files in `src/data/` directory:

- **`src/data/brands.txt`** - Brand inventory (CSV format)
- **`src/data/cars.txt`** - Car inventory (CSV format)

### Data File Formats

#### `brands.txt` Format
```
BrandID , Brand Name , SoundSystem : Price(B)
```

Example:
```
B7-2018, BMW 730Li (2018), Harman Kardon: 3.749B
B5-18, BMW 530i (2018), Alpine: 2.599B
B3-GT18, BMW 320i GT (2018), Bose: 1.799B
```

**Rules**:
- Delimiter: `,` (comma)
- Sound system and price format: `SoundBrand : Price` (colon-separated)
- Price must include "B" suffix (billions)
- Empty lines are ignored
- Invalid records (missing fields, duplicates, malformed prices) are silently skipped during import

#### `cars.txt` Format
```
CarID, BrandID, Color, FrameID, EngineID
```

Example:
```
C01, B7-2018, red, F12345, E12345
C02, B7-2018, black, F12346, E12346
C03, B7-MS, orange, F12347, E12347
```

**Rules**:
- Delimiter: `,` (comma)
- Color: text-only (letters and spaces)
- FrameID: exactly 6 characters (F followed by 5 digits)
- EngineID: exactly 6 characters (E followed by 5 digits)
- All fields are required for valid records
- Duplicate IDs or Frame/Engine IDs cause import failures (records are skipped)
- Brand IDs must exist in brands.txt for referential integrity

### Change Tracking

The application tracks modifications to both brands and cars:
- The `CHANGE` flag is set whenever you add, update, or remove data
- On exit (option 13), if either service has unsaved changes, you're prompted to save
- Use option 12 to manually save at any time
- All changes are written back to the original CSV files on save

---

## Future Improvements

As the application scales, consider these enhancements:

1. **Database Integration**
   - Migrate from CSV files to a relational database (MySQL, PostgreSQL)
   - Support for concurrent access by multiple users
   - Transaction support and ACID compliance
   - Complex queries and reporting

2. **REST API Layer**
   - Add Spring Boot for RESTful API endpoints
   - Enable integration with web/mobile frontends
   - Support JSON data interchange
   - API documentation (Swagger/OpenAPI)

3. **Web User Interface**
   - Develop a web-based UI (React, Vue.js, or Angular)
   - Real-time browser-based dashboard
   - Improved UX with modern input controls and validation
   - Multi-tenancy support

4. **Advanced Features**
   - User authentication and role-based access control
   - Audit logging of all changes (who, what, when)
   - Export/import in multiple formats (JSON, Excel)
   - Advanced filtering and reporting tools
   - Car maintenance history and service records
   - Sales tracking and customer management

5. **Deployment & DevOps**
   - Docker containerization
   - CI/CD pipeline (Jenkins, GitHub Actions)
   - Cloud deployment (AWS, Azure, GCP)
   - Automated testing (JUnit, integration tests)

6. **Code Quality**
   - Unit test coverage (JUnit)
   - Integration tests
   - Automated code analysis (SonarQube)
   - Performance optimization and profiling

---

## Notes

### Assumptions

- **Currency**: All prices are in Vietnamese Dong (VND) expressed in billions (format: `X.XXXB`)
- **Environment**: Application is run from the project root directory. Data files at `src/data/` are required.
- **Character Encoding**: Files use system default encoding (UTF-8 recommended)
- **Single User**: Application operates in single-user mode; no concurrent access support
- **In-Memory Storage**: All data is held in-memory; only persisted when explicitly saved

### Limitations

- **No Database**: File-based storage is limited for large datasets
- **Console-Only**: No graphical user interface
- **Single User**: No multi-user support or permission management
- **No Backup**: Data files are overwritten on save; no backup or versioning
- **Performance**: Large datasets (>10,000 records) may cause noticeable delays
- **Search**: Only supports exact ID matches and substring searches; no advanced query syntax
- **Validation**: Error messages may not cover all edge cases
- **Internationalization**: All UI text is in English only
- **Linux File Paths**: Hardcoded data paths use forward slashes; may need adjustment for Windows testing

### Known Issues

- Invalid records during import are silently skipped with no feedback
- Searching for a brand/car that doesn't exist may take a few seconds
- Price format strictly requires "B" suffix; accepts "b" (lowercase) during import

---

## License

This project is provided as-is for educational and demonstrative purposes.

