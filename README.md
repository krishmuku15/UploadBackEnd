# File Upload & Management Backend

This is a **Spring Boot** backend service for uploading, storing, and managing files.  
It supports:
- Uploading image & video files
- Storing files in the file system
- Storing metadata in **H2 Database**
- Renaming files (with restrictions)
- Fetching all uploaded file metadata

---

## 📦 Tech Stack
- **Java 17+**
- **Spring Boot 3+**
- **Spring Web**
- **Spring Data JPA**
- **H2 Database**
- **Maven**

---

## ⚙️ Project Structure
```
src/main/java/com/example/filedemo/
 ├── controller/   # REST Controllers
 ├── model/        # FileDetails Entity
 ├── repository/   # JPA Repository
 ├── service/      # FileService for upload/rename logic
 └── FileDemoApplication.java  # Main Spring Boot application
```

---

## 🚀 Build & Run

### **1 Prerequisites**
- Install **Java 17** or higher
- Install **Maven 3.8+**
- (Optional) Install an IDE like IntelliJ or Eclipse


---

### **2 Configure Storage Path**
Edit `application.properties`:
```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:filedb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true

# File storage location
file.storage.path=/absolute/path/to/storage/folder
```
⚠️ Make sure the storage folder exists or will be created at runtime.

---

### **3 Build**
```bash
mvn clean install
```

---

### **4 Run**
```bash
mvn spring-boot:run
```
or run the generated JAR:
```bash
java -jar target/filedemo-0.0.1-SNAPSHOT.jar
```

---

### **5 API Endpoints**
| Method | Endpoint            | Description |
|--------|---------------------|-------------|
| POST   | `/files/upload`     | Upload a file (image/video only) |
| GET    | `/files`            | Get all file metadata |
| PUT    | `/files/{id}`       | Rename a file (same extension only) |

---

### **6 Access H2 Database Console**
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:filedb`
- User: `sa`
- Password: *(empty)*

---

## 📤 Deploy

### Local JAR Deployment**
1. Build:
   ```bash
   mvn clean package
   ```
2. Copy `target/filedemo-0.0.1-SNAPSHOT.jar` to the server.
3. Run:
   ```bash
   java -jar filedemo-0.0.1-SNAPSHOT.jar
   ```

---


## 🛠 Development Notes
- Only **image** (`jpg`, `png`, `gif`, etc.) and **video** (`mp4`, `mov`, etc.) formats are allowed.
- Renaming **cannot change file extension**.
- Files are stored both in **filesystem** and **H2 Database**.

---

