### Git Command
* `git pull origin development` : memperbarui project local setiap ada perubahan di branch utama (development).
* `git branch` : mengecek posisi branch saat ini.
* `git checkout namaBranch` : pindah branch baru.
* `git checkout -b namaBranch` : pindah sekaligus membuat branch baru.
* `git add .` atau `git add namaFile1 namaFile2` : menambahkan file ke git local.
* `git commit -m "message"` : menambahkan pesan. Standarisasi commit message ada di laman [ini](https://www.conventionalcommits.org/en/v1.0.0/)
* `git push origin namaBranch` : upload semua file ke github (harus lakukan perintah git add dan git commit dulu). Setelah di push, masuk ke github dan klik tombol **Compare & pull request**
* `git clone https://github.com/ichwansh03/AXA-SmartDrive.git` : cloning project secara keseluruhan
* `git stash` : pending commit, menyimpan sementara file yg sudah diubah di local atau Alt+0 dan Ctrl+Shift+H untuk Shelve Silently.
* `git stash pop` : menampilkan kembali file yg disimpan di stash local atau Alt+0 dan Ctrl+Shift+U untuk Unshelve.
* `git merge namaBranch` : melakukan penggabungan 2 branch yg berbeda.
* `git rebase namaBranch` : melakukan penggabungan 2 branch yg berbeda namun menghapus history merge branch pada file log.
* `git log --oneline --graph` : melihat hash commit-id dan grafik penggabungan dalam satu baris.
* `git commit --amend` : membatalkan commit terakhir dan membuat commit baru dengan perubahan terbaru.
* `git reset HEAD^` : menghapus commit terakhir.
* `git cherry-pick <commit-id>` : untuk memilih commit tertentu dan menerapkan perubahan di commit tersebut ke cabang yang berbeda (rawan conflict).
* `git revert <commit-id>` : membuat commit baru yang membatalkan efek dari commit yang dipilih.  
  
### Modules : [Schema] : [POC]
* Master : mtr : Tengku
* Users : users : Izhar
* HR : hr : Bara
* Customer : customer : Michel
* Service : so : Ichwan
* Partner : partners : Rahman
* Payment : PAYMENT : Giri

### Link One-Drive
SOP Project : [Mini Project](https://codedevid-my.sharepoint.com/:f:/g/personal/dian_code_id/EqJ6Vi_B2cNFllgpmYhC2PcBdZbHHTjTdn7OUFfvI0r5dQ?e=Bv8MeW)

### Project Structure
* java
  * com.app.smartdrive
    * entities
      * master
      * users
      * hr
      * customers
      * serviceOrders
      * partners
      * payments
    * services
        * master
        * users
        * hr
        * customers
        * services
        * partners
        * payments
    * controllers
    * master
        * users
        * hr
        * customers
        * services
        * partners
        * payments
    * dto
    * master
        * users
        * hr
        * customers
        * services
        * partners
        * payments
      * master
      * users
      * hr
      * customers
      * serviceOrders
      * partners
      * payments
    * controllers
      * master
      * users
      * hr
      * customers
      * serviceOrders
      * partners
      * payments
    * dto
      * master
      * users
      * hr
      * customers
      * serviceOrders
      * partners
      * payments
  * resources
    * databases
      * master
      * users
      * hr
      * customers
      * serviceOrders
      * partners
      * payments
