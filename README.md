# Gerva

**Gerva** is a small and very useful library made for building `RecyclerViews` using a single, smart generic `RecyclerViewAdapter`. 

i've seen a lot of projects having a package filled with `RecyclerViewAdapter`'s, each one written with a whole bunch of code involving business logic and UI logic. **Gerva** is made with the thought that every project should have one smart `RecyclerViewAdapter` containing only the code related to building `RecyclerViews`.

#### Problems It Solves

- Eliminating the need for writing all the boilerplate code involved with `RecyclerViewAdapters`.
- Making sure the `RecyclerViewAdapter` is not abused and turned into a massive class containing thousands lines of code and business login.
- Separating UI into small reusable elements, Easy to maintain, extend and bugfix.
- Encapsulates all data binding code into xml files using data binding mechanism.
- Eliminating the need of having more than 1 `RecyclerViewAdapter` in your project :)

## Interfaces

#### `Model` interface

Each `GenericRecyclerViewAdapter` will have it's list of `Model`'s holding data for later binding to the `ViewHolder`.
When the getItemViewType method is called in the `GenericRecyclerViewAdapter`, we return a layout resource id from the model list and later use it to inflate our view.
Classes implementing this interface should also be identifiable via a uniqe identifier of some sort.

#### `ViewHolderFactory` interface

By default, each inflated view is wrapped with a basic view holder, `GenericViewHolder`.
Sometimes when you need some complex UI and logic, you can subclass the `GenericViewHolder` and pass an implementation of `ViewHolderFactory`
to the `GenericRecyclerViewAdapter` constructor, by that telling it which of his items need to be wrapped with your custom view holder.

A better way of understanding this is some example code:

```kotlin
class ExampleViewHolderFactory : ViewHolderFactory {

    override fun createViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenericRecyclerViewAdapter.GenericViewHolder? {

        return when (viewType) {
            R.layout.item_card_view ->
                CardViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        viewType,
                        parent,
                        false
                    )
                )
            else -> null
        }
    }
}

class CardViewHolder(
    override val binding: ItemCardViewBinding
) : GenericRecyclerViewAdapter.GenericViewHolder(binding) {

    override fun bind(
        model: Model?,
        onViewHolderClicked: ViewHolderClickCallback?
    ) {
        super.bind(model, onViewHolderClicked)
        binding.cardView.setOnClickListener { flipCard() }
    }

    private fun flipCard() {
        itemView.animate().scaleY(0f).withEndAction {
            binding.cardContent.rotation += 180f
            itemView.animate().scaleY(1f).withEndAction {
                (listener as? Card.Listener)?.onCardFlipped()
            }
        }
    }
}
```

the createViewHolder method will be called each time a viewHolder is created in the adapter, it allows delegation of the viewHolder construction to allow passing any kind of viewHolder to be built accord to a layout resource id.

## Usage

Let's say we want to show a list of students. Here are the steps for doing so with GERVA:

- Create a data class for our student and implement the model interface.

```kotlin
data class Student(
    override val id: String, 
    val firstName: String, 
    val lastName: String, 
    val dateOfBirth: Date
    ): Model {
    override fun getViewType(): Int = R.layout.item_student
}
```

- Create a xml file for our student layout. 

***Note*** - You must have a data binding layout and a variable named 'model' of the type of your model class in order for the binding of the model class to work!

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
        ...
```

- Create a generic adapter and pass a list of sturdents to it.

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

#### Result

<img width="338" alt="Screen Shot 2022-09-20 at 23 55 28" src="https://user-images.githubusercontent.com/42380097/191362685-6b338582-610a-48be-a903-00d58320ab73.png">

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
