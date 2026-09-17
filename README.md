# Overview

As a software engineer, I wanted to strengthen my mobile development skills by building a real, functional Android application from scratch using Kotlin. This project gave me hands-on experience with Android Studio, fragment-based navigation, UI layout design, event handling, and local data persistence — all core skills for building apps that people actually use day to day.

This app is a **Flashcard Study App** built to help students improve vocabulary and recall in a specific field of study. Users can create their own custom flashcards (question and answer pairs), flip through a deck to test themselves, and mark cards as "mastered" to remove them from the active study rotation.

**How to use the app:**
1. Open the app to the main **Viewer** screen, which shows one flashcard from your active study deck at a time.
2. Tap **Flip Card** to reveal the answer, and tap it again to flip back to the question.
3. Use **Previous** and **Next** to move through the active deck (cards wrap around from the last card back to the first, and vice versa).
4. Tap **Mark as Mastered** to remove the current card from the active rotation — it immediately moves to the Mastered tab.
5. Use the bottom navigation bar to switch to the **Create** tab, where you can add new flashcards by entering a question and an answer and tapping **Save Card**. New cards are added to the active deck right away.
6. Switch to the **Mastered** tab to see every card you've mastered so far. Each one shows its question and answer, along with a **Study Again** button that sends the card back into your active rotation if you want a refresher.
7. All flashcards and their mastered status are saved automatically using local device storage, so your deck and progress are exactly where you left them the next time you open the app — even after a full device restart.

My purpose in creating this app was to go beyond just following a tutorial — I wanted to design and build a complete, working piece of software on my own, applying Kotlin fundamentals and Android UI patterns to solve a real problem (studying vocabulary) in a way I could actually use myself.

# Development Environment

I developed this app using **Android Studio** on Windows, and tested it across multiple **Android Virtual Device (AVD) Emulator** profiles — including phone, small phone, and tablet configurations — to confirm the layout adapted correctly to different screen sizes.

The app is written in **Kotlin**, using native Android UI components including `ConstraintLayout` and `CardView` for the flashcard views, `BottomNavigationView` for screen navigation, and the Fragment framework (`androidx.fragment.app.Fragment`) to structure the app's three screens (Viewer, Create, Mastered) inside a single Activity. **View Binding** is used throughout instead of `findViewById` for safer, more direct view references.

For local data persistence, I used Android's **SharedPreferences**, serializing the flashcard deck to a JSON string (using the built-in `org.json` library) so that all user-created cards and their mastered status survive the app being closed or the device restarting.

# Useful Websites

* [Android Studio Documentation](https://developer.android.com/studio)
* [Activity Lifecycle Guide](https://developer.android.com/guide/components/activities/activity-lifecycle)
* [View Binding Guide](https://developer.android.com/topic/libraries/view-binding)
* [ConstraintLayout Guide](https://developer.android.com/develop/ui/views/layout/constraint-layout)
* [Android Basics in Kotlin](https://developer.android.com/courses/android-basics-kotlin/course)

# Future Work

* Replace SharedPreferences with a Room database as the deck grows, for more robust and queryable storage.
* Allow users to organize flashcards into separate decks by subject, rather than one single shared deck.
* Add the ability to edit or delete existing flashcards, not just create new ones.
* Add spaced-repetition scheduling so mastered cards reappear after a set interval for longer-term retention.
