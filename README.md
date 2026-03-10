# Module-4
## Is TDD flow useful enough?
Menurut saya, flow ini sangat berguna dalam development yang memikirkan dengan baik terkait maintainability karena dalam pengembangan tidak langsung menulis logika kodenya, tetapi mempersiapkan dengan unit-test terlebih dahulu. Hal ini memberikan jaminan bahwa fungsionalitas dan desain sistem akan sesuai sejak awal. Lalu, melalui penulisan unit-test di awal, saya sebagai pengembang juga diberikan kesempatan untuk memikirkan berbagai skenario yang akan terjadi pada program saya. Hal tersebut ditunjukkan dalam pembuatan happy dan unhappy test. Setelah itu, memasuki fase pengimplementasian, penulisan kode juga jauh lebih terbayangkan akan setiap skenario sehingga development lebih straight to the point. Selain itu, saya juga lebih merasa mudah untuk refactoring karena adanya unit-test yang dibuat di awal, saya dapat melakukan uji coba lebih lanjut bahwa apa yang saya modifikasi tidak berdampak buruk ke program utama. 

## Have I followed F.I.R.S.T principle while creating the unit-tests?
Menurut saya unit-test yang dibuat sudah memenuhi prinsip F.I.R.S.T karena
- Semua test berjalan di bawah 1 detik karena tidak ada kondeksi ke database atau jaringan lainnya (Fast)
- Setiap test case berdiri sendiri dan tidak bergantung kepada state testcase lain. Hal ini salah satunya ditandai setting nilai awal pada @BeforeEach (Indpendent)
- Test dapat dijalankan di environment secara berulang (Repeatable)
- Setiap test memiki assertions yang jelas atau tidak perlu melihat konsol secara manual untuk menguji validitasnya (Self-validating)
- Testcase yang dibuat mengikuti TDD cycle sehingga dibuat sebelum fungsinya diimplementasikan (Timely)

# Module-3
## Solid Principles I applied
- **Single Responsibility Principle (SRP)**
  - **Issue awal:** file ProductController.java mengatasi dua controller berbeda, yakni ProductController dan CarController sehingga ada dua tanggung jawab dalam satu file.
  - **How to fix:** saya memisahkan CarController ke file baru, yakni CarController.java sehingga satu file Controller hanya bertanggung jawab ke satu Controller saja.
- **Open/Closed Principle (OCP)**
  - **Issue awal:** Car Repository melakukan metode penyimpanan secara langsung dengan in-memory atau dalam kata lain data disimpan di array secara langsung. Hal ini tidak best practice ketika nantinya program perlu melakukan penyimpanan di database, seperti mySQL atau lainnya.
  - **How to fix:** saya membuat interface CarRepository yang berisi method CRUD, seperti sebelumnya (create, findById, findAll, update, delete) dan membuat class baru, yakni InMemoryCarRepository yang mengimplementasikan interface CarRepository
- **Liskov Substitution Principle (LSP)**
  - **Issue awal:** CarController melakukan ekstensi pada ProductController, tetapi keduanya tidak saling berkaitan sehingga ekstensi tidak memberikan manfaat apa pun
  - **How to fix:** saya menghapus ekstensi yang dilakukan CarController pada ProductController dan juga constructor yang mendefinisikan ProductService sehingga keduanya berdiri secara independen
- **Dependency Inversion Principle (DIP)**
  - **Issue awal:** pada CarController, CarServiceImpl langsung di-autowired. Hal tersebut melanggar konsep DIP karena high level module (controller) tidak boleh bergantung langsung pada low level module (concrete implementation dari Car Service)
  - **How to fix:** saya melakukan pergantian autowired, yakni  ke CarServicenya atau bukan ke implementasi dari CarServicenya sehingga konsep DIP terpenuhi.

## Solid Principles that is already applied
- **Interface Segregation Principle (ISP)**
  - ISP sudah diimplementasikan pada CarService.java yang mana method-method diimplementasikan secara keseluruhan oleh CarServiceImpl.java tanpa adanya satu method pun yang tidak digunakan. Dalam kata lain, contract method yang didefinisikan di CarService.java sudah kohesif dan tidak membuat class yang mengimplementasikannya secara terpaksa harus membuat method tersebut tanpa digunakan.
  - ISP juga sudah saya implementasikan pada pembuatan interface CarRepository juga yang mana method-method CRUD yang didefinisikan sudah kohesif serta berguna secara keseluruhan pada class yang mengimplementasikannya.

## Advantages of applying SOLID Principles
- **Single Responsibility Principle (SRP):** mempermudah maintanability suatu kelas ataupun method. Sebagai contoh, pemisahan antara controller Product dan Car membuat pengembangan ke depan lebih mudah dilakukan karena tanggung jawab controller sudah berdiri pada satu file yang berbeda.
- **Open/Closed Principle (OCP):** meningkatkan fleksibilitas ketika ada penambahan fitur. Sebagai contoh, pembuatan interface CarRepository merupakan langkah awal untuk mendefinisikan kontrak yang dilakukan pada komponen repository yang berhubungan pada Car. Lalu, ketika pengembangan hanya membutuhkan penyimpanan pada In-Memory, maka pengembang dapat melakukan ekstensi pada interface tersebut dan applying methodsnya tanpa mengubah struktur car repository dari awal. Begitu juga, ketika ada penambahan fitur penyimpanan pada media penyimpanan lainnya, seperti database.
- **Liskov Substitution Principle (LSP):** mencegah behaviour yang tidak diperlukan pada inheritance. Sebagai contoh, CarController awalnya melakukan ekstensi pada ProductController yang sebenarnya CarController tidak merepresentasikan apapun dari ProductController sehingga adanya inheritance ini justru hanya mewarisi methods dari ProductController yang tidak digunakan oleh CarController.
- **Interface Segregation Principle (ISP):** menjaga kontrak method tetap spesifik. Sebagai contoh, pembuatan interface CarService sudah menerapkan prinsip ini karena pada implementasinya di CarServiceImpl.java, setiap method diimplementasikan dan digunakan secara utuh sehingga interface sudah mendefinisikan method yang sesuai dengan kebutuhan class yang mengimplementasikannya.
- **Dependency Inversion Principle (DIP):** membuat kode menjadi loosely coupled. Sebagai contoh, pada CarController, injection pada CarService membuat controller tidak perlu memikirkan implementasi service apa yang digunakan di belakang layar sehingga nantinya proses, seperti unit-testing lebih mudah dilakukan karena kita terfokus pada abstraction classnya saja.

## Disadvantages of not applying SOLID Principles
- **Single Responsibility Principle (SRP):** kesulitan dalam pengembangan. Sebagai contoh, jika  controller Product dan Car tidak dipisah, maka proses pengembangan sangat sulit karena kode semakin panjang dan urusan pada satu file sangat banyak.
- **Open/Closed Principle (OCP):** rapuh terhadap fitur baru. Sebagai contoh, jika repository car secara exact menerapkan penyimpanan in-memory, maka ketika ada fitur baru, seperti penyimpanan pada database, struktur kode harus dimodifikasi semua, baik secara constructor maupun methodsnya
- **Liskov Substitution Principle (LSP):** dapat menimbulkan error. Sebagai contoh, CarController yang tetap melakukan ekstensi pada ProductController akan memiliki kemungkinan adanya error karena jika ProductController menambahkan constructor lain, CarController wajib mengimplementasikannya juga. Jika tidak, maka akan error
- **Interface Segregation Principle (ISP):** class akan memiliki method yang sebenarnya tidak digunakan. Sebagai contoh, jika semisalnya ada kontrak method "int sizeBattery()" pada CarService, padahal tidak semua Car merupakan mobil listrik, maka yang ada kita harus mengimplementasikan method tersebut, meskipun method tidak digunakan dengan baik.
- **Dependency Inversion Principle (DIP):** sulit diuji dan diganti. Sebagai contoh, pada injection CarServiceImpl, ketika ternyata yang mengimplementasikan Car Service itu tidak hanya CarServiceImpl, tapi ada beberapa class dan service itu disesuaikan dengan lingkungan production/developmentnya, maka jika kita langsung inject kepada class implementationnya, controller harus berulang kali mengganti service yang digunakan.



# Module-2
## Link-Deployment
- https://selective-carlie-evanhwzorgs-568d8cef.koyeb.app/
## List The Code Quality Issue
- **Empty Method / Missing Nested Comment**
	- **The issue**: terdapat method **setUp()** yang tidak memiliki kode / instruksi pada ProductRepositoryTest.java.
	- **Kenapa:** empty method tidak disarankan oleh sonar karena menimbulkan ambiguitas pada developer lain karena akan muncul asumsi jika kode tersebut antara belum diselesaikan dan harus kosong.
	-**Cara memperbaiki**: Saya menghapus fungsi tersebut karena tidak digunakan sehingga hal ini menghilangkan ambiguitas.
- **Field injection issue**
	- **The issue:** terdapat injection method dengan cara field injection pada ProductController.java (menggunakan @AutoWired langsung pada deklarasi variable untuk class ProductService)
	- **Kenapa:** field injection tidak disarankan oleh sonar karena rawan terjadi NullPointerException ketika terdapat developer atau engineer yang melakukan pemanggilan **new ProductController()**, tetapi tidak memasukkan dependensi terhadap class ProductService
	- **Cara memperbaiki:** mengubah field injection menjadi constructor injection, yakni dengan menghapus anotasi @Autowired, menambahkan sifat final pada variabel ProductService (access-modifiers: private), dan menambahkan anotasi @RequiredArgsConstructor dari Lombok di atas kelas ProductContoller agar constructor dibuat secara otomatis.
- **Unnecessary public modifier in test class**
	- **The issue:** terdapat public modifier pada deklarasi class HomePageControllerTest, ProductControllerTest, dan ProductServiceImplTest.
	- **Kenapa:** Junit 5 sudah tidak membutuhkan adanya tambahan public modifier pada deklarasi class sehingga menghapusnya dapat membuat kode lebih bersih
	- **Cara memperbaiki:** menghilangkan public modifier pada deklarasi classes tersebut.
- **Privileges of Scorecards**
	- **The issue:** terdapat permission "read-all" pada workflow scorecard
	- **Kenapa:** meningkatkan kerentanan / ancaman ketika workflow disusupi dan permission adalah 'read-all' yang tidak hanya membaca kode saja, tetapi keseluruhan aktivitas pada github activites (pull-request, discussions, etc)
	- **Cara memperbaiki:** mengganti 'read-all' menjadi 'read' saja sehingga workflow hanya dapat membaca kode saja.
## Ulasan terkait CI/CD workflows atau Pipeline yang saya buat
Workflows yang saya inisiasi atau bentuk sudah memenuhi definisi Continuous Integration dan Continuous Deployment. Hal tersebut dikarenakan pada Continuous Integration (CI), setiap perubahan kode yang di-push atau di-pull-request ke branch main secara otomatis akan memicu proses build, uji unit-test, dan analisis kualitas kode menggunakan SonarCloud. Ketiga proses ini dilakukan agar perubahan yang ada dapat tervalidasi dan teruji dengan baik sebelum digabungkan ke branch main sehingga potensi bug dapat terdeteksi lebih awal. 

Setelah proses Continuous Integration sudah dilakukan dan semua komponen berhasil, maka dilanjutkan ke proses Continuous Deployment, yakni proses deployment secara otomatis ke lingkungan produksi / production. Penerapan Continuos Deployment ini tidak serta-merta langsung melakukan deploy. Melainkan, ada ketergantungan dengan hasil pada Continuous Integration yang didefinisikan pada bagian "workflow_run" sehingga ketika pada proses Continuous Integration masih gagal, proses deployment tidak akan berlangsung. Selain itu, proses deployment ini juga sudah terintegrasi dengan PaaS koyeb menggunakan koyeb CLI yang otomatis melakukan redeploy pada proses Continuous Deployment ini.

# Module-1
## Refleksi - 1
Setelah melakukan proses implementasi berbagai fitur, seperti Create, Read, Update, dan Delete pada program ini, saya sudah berusaha mencoba menerapkan beberapa prinsip clean code. Hal tersebut diantaranya adalah
  - Meaningful names: 
    - Pada kode di folder repository, saya telah menulis dengan jelas terkait fungsi yang merepresentasikan proses edit dan delete dengan nama "update" dan "delete". Lalu, saya juga menambahkan dua fungsi lain, yakni "findById" untuk mencari produk berdasarkan id product serta "findIndexOf" untuk mencari suatu produk dengan id tertentu terletak di index ke berapa pada tempat penyimpanannya.
- Meaningful comments:
   - Saya sudah tidak lagi melakukan proses dokumentasi komen setiap line, tetapi saya sudah mencoba membuat nama fungsi terkait relevan dengan tugasnya. Kemudian, penambahan comment yang saya lakukan terdapat pada folder repository juga yang memberi informasi bahwa saya membuat method helper pada class ProductRepository. Hal itu, saya lakukan agar tidak membuat kebingungan terhadap developer lain yang akan membaca kode saya karena method tersebut memiliki visibility private tersendiri dan tidak digunakan di luar class.

- Penerapan single responsibility principle: 
   - Kode program yang dibentuk pun telah menerapkan prinsip ini, yakni memisahkan business logic pada folder Service dan manipulasi data pada folder Repository. Salah satu contohnya adalah pada method create di file "ProductRepository.java" tidak ada pengecekan UUID terlebih dahulu sebelum add data ke dalam ArrayList, tetapi pada file "ProductServiceImpl.java" akan dicek terlebih dahulu adanya UUID atau tidak dan menjalankan proses manipulasi data dengan method yang terdapat pada repository.

Selain itu, dalam hal Secure Coding, saya sudah menerapkan proses pembuatan ID menggunakan UUID sehingga hal ini jauh lebih aman ketika terdapat proses manipulasi data yang dilakukan di client (contohnya ketika proses editing yang melibatkan route-path dengan adanya id).

Namun, saya menyadari juga bahwa program saya belum sempurna. Kelemahan program saya, yakni belum adanya input validation dan input sanitation ketika adanya proses manipulasi data melalui client. Maka dari itu, hal yang mungkin terjadi adalah ketidaksesuaian tipe data ketika pada proses client terdapat manipulasi html atau kesalahan input (implikasi dari tidak adanya input validation). Kemudian, ada juga kemungkinan XSS injection yang dapat memanipulasi web yang berjalan di production karena tidak adanya sanitasi input dari user/client. Kemudian, saya juga belum menerapkan error handling sehingga jika terdapat masalah pada program, web dapat langsung menampilkan raw errornya dan hal itu jelas melakukan exposure terhadap program saya.

Maka dari itu, program ini akan memiliki keamanan yang jauh lebih baik jika adanya penambahan input validation, input sanitation, dan error handling yang baik dan informatif terhadap developer program terkait.

## Refleksi - 2
### Refleksi - 2.1
Menuliskan unit-test adalah salah satu pekerjaan yang membutuhkan tenaga lebih karena saya harus membuat beberapa skenario yang mungkin terjadi pada fungsi yang saya buat. Terlebih jika terdapat berbagai conditional yang diterapkan. Namun, ketika melihat "all tests passed", ada kebahagiaan tersendiri karena program terlihat berjalan mulus.

Lalu, menurut saya sendiri, tidak ada exact numbers terkait banyaknya unit tests yang harus dibuat karena proses testing ini bergantung pada kompleksitas logika programnya. Akan tetapi, hal yang dapat menjadi acuan bahwa unit tests yang kita buat dapat memverifikasi program yang diuji adalah dengan melakukan proses pembuatan skenario positif dan negatif. 

Skenario positif adalah skenario di mana program berjalan sukses sesuai keinginan developer. Contohnya, seperti proses pembuatan produk yang berhasil terbuat dan tersimpan di dalam ArrayLists. Skenario negatif adalah skenario ketika program gagal mencapai tujuan utama dari fungsi yang dibuat. Sebagai contoh, kita melakukan uji pembuatan produk kembali, tetapi di-akhir, kita pastikan bahwa produk tidak berhasil dibuat dengan berbagai kondisi yang membuat proses itu gagal. Maka, dengan adanya kedua skenario tersebut, dapat dipastikan bahwa kita sudah memastikan suatu fungsi berjalan sebagaimana mestinya.

Selain itu, code coverage pun sering menjadi tolak ukur untuk memastikan bahwa fungsi yang kita uji benar-benar sudah ter-cover secara keseluruhan. Akan tetapi, code coverage tidak sama dengan memastikan / menjamin program tidak ada bug. Hal tersebut  dikarenakan code coverage hanya melakukan verifikasi bahwa baris kode pada suatu fungsi sudah pernah dijalankan (tidak melihat business logic dari fungsi itu). Oleh sebab itu, masih terdapat kemungkinan bahwa suatu program memiliki bug, seperti kesalahan merepresentasikan fungsi dengan isi utamanya. Contohnya, fungsi pertambahan, tetapi isinya pengurangan dan dalam unit-testnya tetap dibuat sebagaimana isi fungsinya. 

### Refleksi 2.2
Setelah membuat functional testing pada CreateProduct, saya mendapatkan issue terbaru. Hal itu adalah ketika  terdapat requirements pembuatan functional testing yang melakukan verifikasi pada banyak item di product list (dengan setup procedures dan instance variables yang sama). Saya menyadari bahwa akan ada redundant code ketika proses functional testing ini berdiri independen (tanpa adanya inheritance) karena semua setup dan variables tetap sama.

Maka dari itu, menurut saya, akan lebih baik jika program menerapkan proses inheritance. Hal tersebut dapat dilakukan dengan membuat class pondasi, seperti "BaseFunctionalTest" yang berisi setup procedures dan instance variables. Kemudian, setiap functional test, seperti CreateProduct atau semacamnya yang berkaitan dengan functional test dapat membuat class terpisah dan mewarisi setup serta instance variables tersebut dengan proses "extends class" dari BaseFunctionalTest class sehingga file functional test yang digunakan untuk proses pengujian cukup menuliskan fungsi testingnya tanpa melakukan setup kembali / dari awal. Melalui metode ini, dapat dipastikan bahwa kode tidak akan redundant kembali.
