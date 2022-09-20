# Gerva

**Gerva** is a small and very useful library made for building `RecyclerViews` using a single, smart generic `RecyclerViewAdapter`. 

i've seen a lot of projects having a package filled with `RecyclerViewAdapter`'s, each one written with a whole bunch of code involving business logic and UI logic. **Gerva** is made with the thought that every project should have one smart `RecyclerViewAdapter` containing only the code related to building `RecyclerViews`.

#### Problems It Solves

- Eliminating the need for writing all the boilerplate code involved with `RecyclerViewAdapters`.
- Making sure the `RecyclerViewAdapter` is not abused and turned into a massive class containing thousands lines of code and business login.
- Separating UI into small reusable elements, Easy to maintain, extend and bugfix.
- Encapsulates all data binding code into xml files using data binding mechanism.
- Eliminating the need of having more than 1 `RecyclerViewAdapter` in your project :)

#### How it was desined

- How do I know which layout should be inflated for each item in my list? Model solves this with a simple interface that enforces the developer to return a layout resource id representing the layout xml file.

- How do I know what view holder should be used with each layout ? (ViewHolder construction delegation) - The onCreateViewHolder method accepts 2 parameters: parent view group and a viewType integer id. The simple and most common solution is to write a switch case on the view type and create a view holder suitable for each type of your layout id’s. This solution often results with a massive adapter with lots of inner classes for each view holder and it gets messy quickly. Gerva solves this with ViewHolderFactory interface and a generic view holder. By default, each inflated view will get a basic generic view holder - GenericViewHolder. For more complex UI the developer can subclass the generic view holder and pass a ViewHolderFactory implementation to the adapter constructor, telling it which viewHolder should be constructed for a given parent view group and a view type. This enables full flexibility when integrating with the generic adapter by letting the developer build any kind of viewHolder he wants without the adapter knowing which viewHolders he will create.

- How can the user interact with viewHolders built by the generic adapter? Each instance of the GenericViewHolder or his subclasses are constructed with an inflated ViewDataBinding object and a listener of type Any. By this the viewHolder is fully unaware of the interaction it has with other classes outside of the adapter. The ViewDataBinding object is bonded with the listener using data binding and only the ViewDataBinding object knows the concrete type of listener that is being passed in, which will then be casted to it and used for interacting with other classes through it.

#### Bonus Features

- DiffableDataSource - The generic recycler view adapter is build using a AsyncListDiffer which is responsible of computing the difference between two lists via DiffUtil on a background thread.

- ViewHolderClickCallback - the adapter has an optional interface parameter in his initializer. This callback is invoked when a viewHolder is clicked. This can simplify the need for interacting with elements of the recyclerView by clicking them (instead of passing a custom made ActionListener and bind it to a specific model class. The callback also takes a model class and a GenericViewHolder for later use.

- onViewRecycled - this method is overriden in the base adapter and it calls each viewHolder when it is being recycled to let him know if there is any data that need to be disposed and freed from memory.

## Usage

These are the interfaces you need to know to get you started:

#### `Model` interface

Each `GenericRecyclerViewAdapter` will have it's list of `Model`'s holding data for later binding to the `ViewHolder`.
When the getItemViewType method is called in the `GenericRecyclerViewAdapter`, we return a layout resource id from the model list and later use it to inflate our view.
Classes implementing this interface should also be identifiable via a uniqe identifier of some sort.

The following is an example of a simple data class representing Students with a simple layout containing a name and a date of birth.

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

With this model and the `GenericRecyclerViewAdapter`, building a list of students is that easy:

```kotlin
GenericRecyclerViewAdapter(
    listOf(
        Student("1", "will", "Smith", Date(63, 2, 1)),
        Student("2", "Harry", "Potter", Date(72, 11, 3)),
        Student("3", "Dave", "Lawrence", Date(99, 8, 5))
        ...
    ),
    this
).also { recyclerView.adapter = it }
```

<img width="338" alt="Screen Shot 2022-09-20 at 23 55 28" src="https://user-images.githubusercontent.com/42380097/191362685-6b338582-610a-48be-a903-00d58320ab73.png">

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
