package com.tui.app.network.modal


data class SelectionModal(
    var fromCurrency:String="USD",
    var toCurrency:String="GBP",
    var amount:String="",
    var conversionAmount:String="",
    var rate: Double =0.0
) {
    fun clear() {
        this.fromCurrency ="USD"
        this.toCurrency ="GBP"
        this.amount =""
        this.conversionAmount =""
        this.rate =0.0
    }
}