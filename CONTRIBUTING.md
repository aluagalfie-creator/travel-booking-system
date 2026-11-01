Pull Request Guide for Group Members
Travel Booking System, DSA group project. Our professor requires every member to have their own pull request (PR) into `main`. Nobody pushes directly to `main`.
The idea in one picture
Branch: make your own copy of the code to work on.
Commit and push: save your work and upload your branch.
Pull request: ask the leader to add your branch into `main`.
Leader merges: your work becomes part of `main`, with your name on it.
`main` is the finished version the professor checks, so only tested work goes in.
Our branches
Member	Branch name	Your files
Alyanna	`alyanna-flights`	FlightBST, FlightDB, FlightCatalog, FlightPanel
Cedie	`feature/customers`	CustomerHashTable, UserDB, CustomerRegistry, RegisterFrame, CustomerPanel
Rhyjel	`feature/hotels-packages`	PackageSorter, PackageDeal, HotelDB, HotelPanel, PackagePanel
Cristofer	`feature/bookings`	BookingDB, BookingService, BookingPanel, HistoryPanel, InvoiceFrame, ReportPanel, TripHeap
The leader (Alfie) has `feature/leader-files`. Use your own branch name, and keep using the same branch for your whole part.
One-time setup (do this once)
Accept the GitHub invite and install Git (see the Tools Setup Walkthrough).
Open Command Prompt and set your name and email. Use the same email as your GitHub account so your commits show your profile:
```
git config --global user.name "Your Name"
git config --global user.email "your-github-email"
```
Clone the project. Open the folder where you keep projects in File Explorer, click the address bar, type `cmd`, press Enter, then run:
```
git clone https://github.com/aluagalfie-creator/travel-booking-system.git
```
Sign in through the browser window that opens.
How to open Command Prompt in your project folder
Open the project folder in File Explorer (the one containing `pom.xml`).
Click the address bar at the top.
Type `cmd` and press Enter.
Every command below is typed there.
Part 1: Start your branch (once, before you begin working)
```
git checkout main
git pull origin main
git checkout -b feature/yourbranch
```
Replace `feature/yourbranch` with your branch name from the table. You should see `Switched to a new branch`.
If your branch already exists, just switch to it:
```
git checkout feature/yourbranch
```
Part 2: Do your work in NetBeans
Create each class by right-clicking its package (`ds`, `db`, `model`, `service`, `gui`), so the `package` line fills in by itself.
Put every file in the package listed in the README.
Edit only your own files.
Follow the project rules: `setLayout(null)`, no drag-and-drop, no `HashMap` or `Collections.sort` in the data structure code.
Run Clean and Build and make sure it says `BUILD SUCCESS` before you commit.
Part 3: Save and upload your work
```
git status
git add .
git commit -m "Add FlightBST insert and search"
git push --set-upstream origin feature/yourbranch
```
`git status` shows what changed. Check that there is no `target/` folder and no change to `DBConnection.java`.
Write a clear commit message that says what you did.
The `--set-upstream` part is only needed the first time you push a branch. After that, `git push` is enough.
If a Connect to GitHub window appears, click Sign in with your browser.
Commit and push small and often, not just once at the end.
Part 4: Open your pull request
Only do this when your part builds and works.
Go to `github.com/aluagalfie-creator/travel-booking-system` and log in.
Click the yellow banner Compare & pull request. If it is not there, click the Pull requests tab, then New pull request.
Set base: `main` on the left and compare: your branch on the right. The arrow must point into `main`.
Type a clear title, for example: `Add FlightBST and flight admin panel`.
Type a short description: what you added, and how to test it.
Click Create pull request.
Click the Files changed tab and check that only your files are listed.
Message the leader that your PR is ready.
PR description template
Copy this into the description box:
```
## What I added
- (list your classes and screens)

## How to test
1. (step to run it)
2. (what you should see)

## Notes
- (anything the leader should know)
```
Part 5: After you open the PR
The leader (Alfie) will:
Pull your branch and run Clean and Build.
Run the app and test your screen.
Either merge it, or leave a comment asking for a fix.
If the leader asks for changes
Do not open a new PR. Fix the problem on the same branch, then push again:
```
git add .
git commit -m "Fix review comments"
git push
```
The PR updates by itself.
If it says there are merge conflicts
Someone else's work was merged first. Update your branch:
```
git checkout main
git pull origin main
git checkout feature/yourbranch
git merge main
```
If Git reports a conflict, open the file in NetBeans. Keep the correct lines and delete the `<<<<<<<`, `=======`, and `>>>>>>>` markers. Then run:
```
git add .
git commit -m "Resolve merge conflict"
git push
```
Ask the leader for help if you are not sure which lines to keep.
Part 6: After your PR is merged
Update your computer so your next piece of work starts from the latest code:
```
git checkout main
git pull origin main
```
Run Clean and Build. If you have more work, go back to Part 1 and use the same branch, or make a new one such as `feature/yourbranch-2`.
Rules for everyone
Never push directly to `main`. Always use a branch and a pull request.
Only edit your own files. If you need a change in someone else's file, message the owner.
Do not edit `MainFrame`, `LoginFrame`, or `TravelSystem`. Message the leader, who connects your panel after your PR is merged.
Pull before you start every day: `git pull origin main`.
Never commit the `target/` folder, or a local change to your `DBConnection.java` password.
Use the packages in the README (`model`, `ds`, `db`, `service`, `gui`). To move a file, use Refactor → Move in NetBeans, not drag-and-drop.
Make sure your code builds before opening a PR.
Everyone must be able to explain their own part at the presentation, and the other parts too.
Quick command cheat sheet
What you want	Command
Get the latest code	`git pull origin main`
Make your branch (first time)	`git checkout -b feature/yourbranch`
Switch to your branch	`git checkout feature/yourbranch`
See what changed	`git status`
Stage everything	`git add .`
Save a snapshot	`git commit -m "message"`
Upload your branch (first time)	`git push --set-upstream origin feature/yourbranch`
Upload your branch (later)	`git push`
Common problems
Problem	Fix
`Repository not found`	You have not accepted the GitHub invite, or you are logged in to the wrong account. Check `github.com/aluagalfie-creator/travel-booking-system/invitations`
`The current branch has no upstream branch`	Use `git push --set-upstream origin feature/yourbranch`
`src refspec ... does not match`	You have not committed yet. Run `git add .` and `git commit` first
No yellow Compare & pull request banner	Go to Pull requests → New pull request and pick your branch
There isn't anything to compare	Your branch has no new commits. Commit and push again
`Updates were rejected`	Run `git pull origin main`, then push again
`Author identity unknown`	Set your name and email (one-time setup)
Asks for a username and password	Open Credential Manager → Windows Credentials, remove any `git:https://github.com` entries, then push again and sign in through the browser
Your package is not showing on GitHub	An empty package has no file. Add a real class to it
`package x does not exist` after pulling	Run Clean and Build, and check the folder name is lowercase
Before you open your PR: checklist
[ ] Clean and Build says `BUILD SUCCESS`
[ ] The app runs and your screen works
[ ] Every file is in the right package
[ ] `setLayout(null)` is used in every frame and panel, with `setBounds()` on every component
[ ] No `HashMap`, `Collections.sort`, or other built-in collections in the data structure code
[ ] No `target/` folder and no local password change in the commit
[ ] Title and description are clear
[ ] You can explain how your code works
