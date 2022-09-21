<p align="center">
  <img width="300" height="300" src="https://user-images.githubusercontent.com/42380097/191611740-dcbb3f4c-173e-43e2-a810-a543abf7d3f0.png">
</p>

# Gerva

**Gerva** is a small and very useful library made for building `RecyclerViews` using a single, smart generic `RecyclerViewAdapter`. 

## Key Features

- Eliminating the need for writing all the boilerplate code involved with `RecyclerViewAdapters`.
- Making sure the `RecyclerViewAdapter` is not abused and turned into a massive class containing thousands lines of code and business login.
- Separating UI into small reusable elements, Easy to maintain, extend and bugfix.
- Encapsulates all data binding code into xml files using data binding mechanism.
- Eliminating the need of having more than 1 `RecyclerViewAdapter` in your project :)

## How To Use Gerva?

Let's say we want to show a list of students.
The simplest use case would looke something like this:

```kotlin
GenericRecyclerViewAdapter(
    listOf(
        Student("1", "will", "Smith", Date(63, 2, 1)),
        Student("2", "Harry", "Potter", Date(72, 11, 3)),
        Student("3", "Dave", "Lawrence", Date(99, 8, 5))
        ...
    )
).also { recyclerView.adapter = it }
```

###### Result:

<img width="338" alt="Screen Shot 2022-09-20 at 23 55 28" src="https://user-images.githubusercontent.com/42380097/191362685-6b338582-610a-48be-a903-00d58320ab73.png">

#### `Model`

The GenericRecyclerViewAdapter takes in a list of Models, `Model` is an interface that represents each item of that list.

```kotlin
interface Model: Identifiable {
    fun getViewType(): Int
    fun areItemsTheSame(otherItem: Model): Boolean = this.id == otherItem.id
    fun areContentsTheSame(otherItem: Model): Boolean = this == otherItem
}

data class Student(
    override val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: Date
    ): Model {
    
    override fun getViewType(): Int = R.layout.item_student
    
    // Checks for same identifier
    override fun areItemsTheSame(otherItem: Model): Boolean = (otherItem as? Student)?.id == this.id
    
    // Checks for same UI data - called only if `areItemsTheSame` returns true
    override fun areContentsTheSame(otherItem: Model): Boolean = this == otherItem
}
```

In order of getting your `Model` injected to your layout with DataBinding, **You must use a data binding layout and add a variable named model:**

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="model"
            type="com.ziv_nergal.gerva.model.Student" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="10dp">
        
        <TextView
            android:id="@+id/firstName"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:text="@{model.firstName}" />
            ...
```

Now let's say we want to add anothe view to our list, a card that flips every 3 seconds:

```kotlin
GenericRecyclerViewAdapter(
    arrayListOf(
        Student("1", "Will", "Smith", Date(63, 2, 1)),
        Student("2", "Harry", "Potter", Date(72, 11, 3)),
        Student("3", "Dave", "Lawrence", Date(99, 8, 5)),
        Card()
    ),
    viewHolderFactory = MyViewHolderFactory()
).also { recyclerView.adapter = it }
```
We use the ViewHolderFactory interface here to tell the adapter that we need a custom viewHolder for the card view:

```kotlin
interface ViewHolderFactory {
    fun createViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenericRecyclerViewAdapter.GenericViewHolder?
}

class MyViewHolderFactory : ViewHolderFactory {

    override fun createViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenericRecyclerViewAdapter.GenericViewHolder? {

        return when (viewType) {
            R.layout.item_card_view ->
                CardViewHolder(
                    ItemCardViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
                
            // Returning null will tell the adapter to use the base GenericViewHolder
            else -> null
        }
    }
}

class CardViewHolder(
    override val binding: ItemCardViewBinding
) : GenericRecyclerViewAdapter.GenericViewHolder(binding) {

    override fun bind(model: Model?) {
        super.bind(model)
        flipCard()
    }

    private fun flipCard() {
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                itemView.animate().scaleY(0f).withEndAction {
                    binding.cardContent.rotation += 180f
                    itemView.animate().scaleY(1f).withEndAction {
                        (listener as? Card.Listener)?.onCardFlipped()
                    }
                }
                mainHandler.postDelayed(this, 3000)
            }
        })
    }
}
```

###### Result:

<p align="start">
  <img width="300" src="https://user-images.githubusercontent.com/42380097/191613126-8d3210f6-b747-49dd-a87b-f06e8e4488db.gif">
</p>

One more thing, we now want to create another in our list that when clicked, he shuffles the order of the recyclerView items:

```kotlin
data class Button(
    val title: String,
    val subtitle: String? = null,
    val mainIcon: Int,
    val secondaryIcon: Int? = null
) : Model {

    interface Listener {
        fun onButtonClicked(button: Button)
    }
    
    override val id: String = UUID.randomUUID().toString()

    override fun getViewType(): Int = R.layout.item_button
}
```

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:bind="http://schemas.android.com/apk/res-auto"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>

        <variable
            name="model"
            type="com.ziv_nergal.gerva.model.Button" />

        <variable
            name="listener"
            type="com.ziv_nergal.gerva.model.Button.Listener" />

    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="@{()-> listener.onButtonClicked(model)}">
        ...
```

We can create an interface and pass it to the adapter as an Any object. the interface will be later binded with our layout so we can use it to notify listeners on user interaction with our views. now, in our main activity we implement that interface and pass it to the adapter:

```kotlin
val models = arrayListOf(
    Student("1", "Will", "Smith", Date(63, 2, 1)),
    Student("2", "Harry", "Potter", Date(72, 11, 3)),
    Student("3", "Dave", "Lawrence", Date(99, 8, 5)),
    Card(),
    Button(
        "Shuffle",
        "Click to shuffle",
        R.drawable.ic_baseline_flip_camera_android_24
    )
)

private fun initGenericRecyclerViewAdapter() {
    GenericRecyclerViewAdapter(
        models,
        listener =  this@MainActivity,
        viewHolderFactory = ExampleViewHolderFactory()
    ).also { recyclerView.adapter = it }
}

override fun onButtonClicked(button: Button) {
    (recyclerView.adapter as? GenericRecyclerViewAdapter)?.updateData(models.shuffled())
}
```

###### Result:

<p align="start">
  <img width="300" height="300" src="https://user-images.githubusercontent.com/42380097/191616359-ccb044cf-cf49-42f5-a2e2-a4191d05659c.gif">
</p>

## Installation

- Add the JitPack maven repository to the list of repositories
- Add the dependency information

Gradle example:

```kotlin
allprojects {
   repositories {
       mavenCentral()
       maven { url "https://jitpack.io" }
   }
}

dependencies {
   implementation 'com.github.User:Repo:Version'
}
```

## Requirements

- view & data binding enabled
```kotlin
buildFeatures {
    viewBinding true
    dataBinding true
}
```

## Author

Ziv Nergal, ziv32155@gmail.com

## License

Gerva is available under the MIT license. See the LICENSE file for more info.
