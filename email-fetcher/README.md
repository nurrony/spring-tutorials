# Email Service

This Repository contains code that fetches emails from an email using IMAP Protocol

## Prerequisites

1. Java 17.x.x
2. Maven 3.9.x
3. EMAIL Service with IMAP support

### ℹ️ Note on GMail IMAP

If you are using GMAIL IMAP to fetch the email. You must ensure these following before running the application

1. Please make sure you have Enabled 2FA
2. Generate `App Password` before running this Application.
3. Enable IMAP Settings in GMail

## ⚙️ Setup

1. Create a file called `application.yml` in [src/main/resources](src/main/resources/) directory
1. Copy the content [src/main/resources/sample.application.yml](src/main/resources/sample.application.yml) into `application.yml`
1. Change the values accordingly
1. Then run project in your favourite IDE

You can run the project using CLI by running the following command

```
./mvnw spring-boot:run
```

> ℹ️ The code has no warnings. A kind request to the developer not leaving a single warning as well. 🙏

## 📚 Development Guideline

You **MUST** agree and follow these styleguides

### ✅ Coding Style

- There is a file called `itc-java-formatter.xml` (a extended version of Eclipse Java Coding Style). Make sure that your Editor's setting is aligned with it.
- Must install [Editorconfig](https://editorconfig.org/#download) plugin for your IDE. If you use Spring Tool Suite please use Eclipse Plugin
- A class should consist less than 10 methods, never more than 15.
- A class Must not exceed 250 lines.
- A class and Methods must do what it is intend to do and do one thing.
- Methods must not contains more that 30 lines.
- Try your best to write Pure Method (a method that does not have side-effect outside of its lexical scope)
- If-Else/Switch
  - Best if you can avoid
  - Never nested more than 3 levels
  - Try to avoid using `else` block. IMHO, this is an unnecessary keyword
- Method and variable must reflects its purpose by reading the name. `VeryVeryLongMethodAndVariableName` is not a problem
- Never declare unnecessary variables or leave unused variables in code.
- Avoid writing concise/clever code in favor of readability.
- Please try your best to be consistent and write code for human.
- Code must not have any warning
- Each feature and its related Resources(aka Controller), Services, Repositories must be compartmentalized in its own module.
- Prefer writing code in declarative way over comparative way.
- Prefer Composition over inheritance
- **DO NOT** handle/engulf exception/error in lower layer. If you have to handle any exception, it must have a good reason. Better leave it for API consumer aka client side to take decision on its behalf.

## 🤝 RESTful API Guideline

We follow this RESTful API Guideline and each API must follow this [RESTful API Guideline](https://docs.google.com/document/d/1Pn0ASHaPZszQ_y2KdrnlzWZmcPF-8aV0cfstXlMxsuE/edit?usp=sharing)

## 💻 Feature Trackers

- [x] Fetches Emails
- [x] Parse Email
- [x] Fetch Email by keyword in subject
- [x] Parse Text based attachments and append to the contents
