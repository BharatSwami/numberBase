package converter
import java.util.*
import java.math.BigInteger
import java.math.BigDecimal
import java.math.RoundingMode
import java.math. MathContext

fun toDecimal(num: String, base: BigInteger): BigInteger {
    var decimalNumber = BigInteger.valueOf(0)
    var value = BigInteger.valueOf(0)
    if (base == BigInteger.valueOf(10)) {
        return BigInteger(num)
    }
    for (i in 0..num.lastIndex) {
        if (num[i] in "abcdefghijklmnopqrstuvwxyz") {
            value = num[i].toInt().toBigInteger() - BigInteger.valueOf(87)
        } else {
            value = num[i].toString().toBigInteger()
        }
        var powerOfTwo = BigInteger.valueOf(1)
        for (j in 1..num.lastIndex -i) {
            powerOfTwo *= base
        }
        decimalNumber += value*powerOfTwo
    }
    return decimalNumber
}

fun toFractionalDecimal(num: String, base: BigDecimal): BigDecimal {
    var fractionNumber = BigDecimal(0)
    var value = BigDecimal(0)
    if (base == BigDecimal(10)) {
        return BigDecimal(num)
    }
    for (i in 0..num.lastIndex) {
        if (num[i] in "abcdefghijklmnopqrstuvwxyz") {
            value = num[i].toInt().toBigDecimal() - BigDecimal(87)
        } else {
            value = num[i].toString().toBigDecimal()
        }
        var n = i + 1
        fractionNumber += value * base.pow(-n, MathContext.DECIMAL64)
    }
    //val list = fractionNumber.toString().split(".")
    return fractionNumber//list[1].toBigDecimal()
}

fun fromDecimal(num: BigInteger, base2: BigInteger): String {
    var ans = ""
    var mod = BigInteger.valueOf(0)
    var number = num
    if (number == BigInteger.valueOf(0)) {
        return number.toString()
    }
    while (number > BigInteger.valueOf(0)) {
        mod = number % base2
        number = number / base2
        if (mod > BigInteger.valueOf(9)) {
            ans += (BigInteger.valueOf(87) + mod).toInt().toChar().toString()
        } else {
            ans += mod.toString()
        }
    }
    return ans.reversed()
}

fun fromFractionalDecimal(num: BigDecimal, base: BigDecimal): String {
    var ans = ""
    var value = BigDecimal.valueOf(0)
    var number = num
    var integerPart = BigDecimal.valueOf(0)
    var decimalPart = BigDecimal.valueOf(0)
    for (i in 1..5) {
        value = number * base
        integerPart = value.setScale(0, RoundingMode.FLOOR)
        decimalPart = value - integerPart
        if (integerPart > BigDecimal.valueOf(9)) {
            ans += (BigDecimal.valueOf(87) + integerPart).toInt().toChar().toString()
        } else {
            ans += integerPart.toString()
        }
        number = decimalPart
    }
    return ans
}

fun main() {
    val scanner = Scanner(System.`in`)
    
    while (true) {
        print("Enter two numbers in format: {source base} {target base} (To quit type /exit)")
        val sourceBase = scanner.next()
        if (sourceBase == "/exit"){
            break
        } else {
            var triger = true
            val targetBase = scanner.next()
            while (triger) {    
                print("Enter number in base ${sourceBase} to convert to base ${targetBase} (To go back type /back)")
                var number = scanner.next() //type : String
                if (number == "/back") {
                    println("")
                    triger = false
                } else {
                    val list = if ("." in number) number.split(".") else listOf<String>(number)
                    val output1 = fromDecimal(toDecimal(list[0].toString(), sourceBase.toBigInteger()),targetBase.toBigInteger())
                    if (list.size > 1) {
                        val output2 = fromFractionalDecimal(toFractionalDecimal(list[1].toString(), sourceBase.toBigDecimal()),targetBase.toBigDecimal())
                        //val output2 = toFractionalDecimal(list[1].toString(), sourceBase.toBigDecimal())
                        println("Conversion result: $output1.$output2")
                    } else {
                        println("Conversion result: $output1")
                    }
                    
                    println("")
                }       
            }
        }   
    } 
}
