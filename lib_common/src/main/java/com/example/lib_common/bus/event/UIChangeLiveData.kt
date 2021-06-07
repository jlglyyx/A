package com.example.lib_common.bus.event

class UIChangeLiveData {

    val showLoadingEvent:SingleLiveEvent<String> = SingleLiveEvent()

    val dismissDialogEvent:SingleLiveEvent<Boolean> = SingleLiveEvent()
}