# Harry Potter the Magic World 
![harry-potter](data/harry-potter.jpg)
## Proposal
This project is a simple *Harry Potter* theme role-playing video game. I named it *Harry Potter the Magic World*. 
Users can choose their favourite wizard to fight against evil enemies by choosing a spell to in each round. Different 
spells have different ATK\(attack) to the enemy, and if the HP\(health point) of a character less than or equals to 
zero, then the character dies. Each wizard and enemy also has unique HP and ATK from others, so the difficulty of each 
battle is distinct. Moreover, there will be a simple plot of the game so that it will not be boring. To be specific, 
the enemies the player will encounter will get stronger and stronger, and the player must beat previous ones to fight 
against the next enemy. Everyone who is interested in *Harry Potter* can enjoy this game. This game can be expanded by 
adding more battles and more attracting plot, as well as different mode and more elements like shopping system can be 
added in the future maintenance. I picked this project because I am into playing video games and both of *Harry Potter* 
series books and movies attract me a lot.

## Phase 1 - User Stories
- As a user, I want to be able to create a new archive and add it to the saving slot.
- As a user, I want to be able to view all my archives and remove one or some of them.
- As a user, I want to be able to save my game process into my current archive.
- As a user, I want to be able to view all my archives and read one of them then continue to play.
- As a user, I want to be able to view wizards options and choose a wizard at the beginning of the game.
- As a user, I want to be able to view all the spells that I can use and choose four of them to use before each battle.
- As a user, I want to be able to know the HP of both my wizard and the enemy at the beginning of each round 
  during the game.
- As a user, I want to be able to view four of my chosen spells and use one of them to attack
- As a user, I want to be able to cause different damage to the enemy in each round even using the same spell.
- As a user, I want to ba able to feel the difference when using different wizards and fighting against different
  enemies, \(i.e. each character has distinct HP and ATK).
- As a user, I want to be able to know if I win a battle. If I lose, I want to restart the battle.
- As a user, I want to get stronger in the gaming process, which means my hp and attack point can increase 
  after each battle.
- As a user, I want to view the plot of the game, which can make the game fun.
- As a user, I want to go back to create/remove/read archive panel after each battle, and my data can be  automatically 
  saved.
  
## Phase 2 - User Stories
- As a user, I want to be able to save my archives to a file.
- As a user, I want to be able to optionally load my archive file and continue to play the gam when the program starts.
- As a user, I want to be able to remove one or several archives from the file.
- As a user, I want to be able to automatically save my data when I want to quit the game.

## Instructions for Grader
- You can generate the first required event \(i.e. new an archive) by clicking the "New Game" button on the main panel, 
  then click "Create New Archive" button and enter your archive name in the pop-up window before you click OK. You can 
  also see all your archives in the scroll-panel.
- You can generate the second required event by double-clicking the "Continue" button after you create your archive
  to start the game. Also, You can remove your archive by clicking the "Delete Archive" in the main panel, then select
  the archive that you want to delete and click the "Delete Archive" button. You will see that the archive was deleted.
- You can locate my visual component by getting into the selecting wizard panel.
  After you create your new archive, you will select your wizard by clicking the button under the icon of each wizard.
  You can continue the game by double-click the "Continue" after select your wizard.
- You can reload one archive and continue the game by clicking the "Continue Game" button on the main panel, then 
  select your archive and click "Read Archive" button to load your archive. After the pop-up window shows up, you can 
  double-click "continue" button to continue your game.
- You can save your data by clicking the "Save Game" button on the main panel. Also, the data is automatically saved 
  when you exit the game either by the "Quit Game" button on the main panel, or the close button of the program.
  
## Phase 4: Task 2
- Use Map in SavingSlot class to store archives, and in Archive class to store spells.
- Include a type hierarchy in the code. ArchiveManagementPanel, ContinueGamePanel, NewArchivePanel, SelectWizardsPanel 
  and StartPanel are subclasses of the GamePanel class.
  
## Phase 4: Task 3
- Move paintComponent method in ArchiveManagementPanel, ContinueGamePanel, NewArchivePanel, SelectWizardsPanel and 
  StartPanel to the GamePanel to decrease the coupling.
- Extract removeArchive method from initializeRemoveButton method in ArchiveManagementPanel class to decrease coupling. # Harry_Potter_Game
# Harry_Potter_Game
# Harry_Potter_Game
# Harry_Potter_Game
