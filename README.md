# nightgamesmod stable

My previous rewrite attempt in explicit-loops stalled, partly due to trying to keep up with features and systems added on other forks.

This stable branch is feature-frozen at v2.5.1.2 of nergantre's master branch. Commits to this branch will be either bugfixes or refactoring.

## Steps taken/planned

1. **DONE** Some tests are failing or broken. Restore to green status.

2. **DONE** Move game logic out of GUI. For this step, code reaching into the gui package is fine; code reaching out is not. Might look uglier than before.

3. **DONE** Break up the Global monstrosity. Find similar parts and move them to their own classes/packages.

    This looks like a good time to do some post-move cleanup.

4. Make the game loop explicit, or at least document it.

5. Separate display and game logic. There will need to be a layer between display logic and game logic, although exactly where that code lives is less important than the conceptual structure.

6. Migrate combat to an event-based system, or at least some sort of system. Will enable step 7.

7. Make the effects of a trait or status viewable in a single location.

8. man who even knows

9. anything that isn't done yet will probably change
