package com.nguyen.roomwithaview

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    // The LiveData is initialized with the allWords Flow from the Repository. then convert the Flow
    // to LiveData by calling asLiveData()
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    // a wrapper insert() method that calls the Repository's insert() method. In this way, the
    // implementation of insert() is encapsulated from the UI. We're launching a new coroutine and
    // calling the repository's insert, which is a suspend function. ViewModels have a coroutine
    // scope based on their lifecycle called viewModelScope
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}

// To create the ViewModel we implement a ViewModelProvider.Factory that gets as a parameter the
// dependencies needed to create WordViewModel: the WordRepository
// By using viewModels and ViewModelProvider.Factory then the framework will take care of the
// lifecycle of the ViewModel. It will survive configuration changes and even if the Activity is
// recreated, you'll always get the right instance of the WordViewModel class
class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}