package gvfraction

import com.sun.javaws.exceptions.InvalidArgumentException
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.math.abs

class porterajFraction() : FractionOperator {

        var wholeF = 0
        var numeratorF = 0
        var denominatorF = 1
        var positive = true

    constructor(whole: Int) : this() {
        wholeF = whole
        numeratorF = 0
        denominatorF = 1
        positive = positiveCheck(whole, 0)
    }
    constructor(whole: Int, numerator: Int, denominator: Int, positiveF: Boolean) : this() {
        wholeF = whole
        numeratorF = numerator
        denominatorF = denominator
        positive = positiveF
    }
    constructor(s : String) : this() {
        val iterateS = s.length - 1
        for (i in 0..iterateS) {
            if(!Character.isDigit(s[i]) && s[i] != '-' && s[i] != ' ' && s[i] != '/') {
                fail("Contains illegal character")
            }
        }
        var space = s.indexOf(' ')
        if (space != -1) {
            var slash = s.indexOf('/')
            if (slash == -1) {
                fail("Space but no slash")
            }
            else {
                var wholeS = s.substring(0, space)
                var numeratorS = s.substring(space + 1, slash)
                var denominatorS = s.substring(slash + 1, s.length)

                wholeF = wholeS.toInt()
                numeratorF = numeratorS.toInt()
                denominatorF = denominatorS.toInt()
                positive = positiveCheck(wholeF, numeratorF)
                if (denominatorF <= 0) {
                    fail("Zero denominator")
                }
                return
            }
        }
        var slash = s.indexOf('/')
        if (slash != -1) {
            var numeratorS = s.substring(0, slash)
            var denominatorS = s.substring(slash+1, s.length)

            numeratorF = numeratorS.toInt()
            denominatorF = denominatorS.toInt()
            wholeF = 0
            positive = positiveCheck(wholeF, numeratorF)
            if (denominatorF <= 0) {
                fail("Zero denominator")
            }
            return
        }
        wholeF = s.toInt()
        numeratorF  = 0
        denominatorF = 1
        positive = positiveCheck(wholeF, numeratorF)
    }

    override fun numerator(): Int {
        return abs(numeratorF)
    }

    override fun denominator(): Int {
        return abs(denominatorF)
    }

    override fun isPositive(): Boolean {
        return positiveCheck(wholeF, numeratorF)
    }

    override fun makeProper() {
        var toAdd = numeratorF / denominatorF
        if (positive) {
            wholeF = wholeF + toAdd
            numeratorF = numeratorF % denominatorF
        }
        else {
            if (wholeF != 0) {
                wholeF = wholeF - toAdd
                numeratorF = numeratorF % denominatorF
            }
            else {
                wholeF = toAdd
                numeratorF = numeratorF % denominatorF
            }
        }
        if (numeratorF == 0) {
            denominatorF = 1
        }
        positive = positiveCheck(wholeF, numeratorF)
    }

    override fun isProper(): Boolean {
        if ((numeratorF > denominatorF))
            return false
        if(numeratorF == 0 && denominatorF == 1)
            return false
        return true
    }

    override fun toProper(): FractionOperator {
        var a: FractionOperator = porterajFraction(wholeF, numeratorF, denominatorF, positive)
        a.makeProper()
        return a
    }

    override fun reduce() {
        var n = abs(numeratorF)
        var d = abs(denominatorF)
        var gcd = GCD(n, d)
        numeratorF = numeratorF / gcd
        denominatorF = denominatorF / gcd
        positive = positiveCheck(wholeF, numeratorF)
    }

    override fun isReduced(): Boolean {
        var a: FractionOperator = porterajFraction(wholeF, numeratorF, denominatorF, positive)
        a.reduce()
        if (a.numerator() != numerator()) {
            return false
        }
        if (a.denominator() != denominator()) {
            return false
        }
        if (a.isPositive() != isPositive()) {
            return false
        }
        return true
    }

    override fun plus(other: FractionOperator): FractionOperator {
        var productThis = wholeF * denominatorF
        var productOther = other.whole() * other.denominator()
        if (!(other.isPositive())) {
            productOther = - productOther
        }
        var thisN = numerator()
        var otherN = other.numerator()

        if(!(positive)) {
            thisN = -thisN
        }
        if(!(other.isPositive())) {
            otherN = -otherN
        }
        thisN = productThis + thisN
        otherN = productOther + otherN

        var newDenom = denominatorF * other.denominator()
        thisN = thisN * other.denominator()
        otherN = otherN * denominatorF
        var newNumer = thisN + otherN
        var p = positiveCheck(0, newNumer)
        var a : FractionOperator = porterajFraction(0, newNumer, newDenom, p)
        a.makeProper()
        a.reduce()
        return a

    }

    override fun plus(other: Int): FractionOperator {
        var numProd = other * denominatorF
        var addN = wholeF * denominatorF
        var oldN = numeratorF
        if (!(isPositive())) {
            oldN = -oldN
        }
        var newN = addN + oldN + numProd
        var p = positiveCheck(0, newN)
        var a : FractionOperator = porterajFraction(0, newN, denominatorF, p)
        a.reduce()
        a.makeProper()
        return a
    }

    override fun minus(other: FractionOperator): FractionOperator {
        var b = this + -other
        b.reduce()
        b.makeProper()
        return b
    }

    override fun minus(other: Int): FractionOperator {
        var a = this + -other
        a.reduce()
        a.makeProper()
        return a
    }

    override fun unaryMinus(): FractionOperator {
        var w = wholeF
        var n = numeratorF
        if (wholeF != 0) {
            w = -w
        }
        else {
            n = -n
        }
        var p = positiveCheck(w, n)
        var a: FractionOperator = porterajFraction(w, n, denominatorF, p)
        a.makeProper()
        a.reduce()
        return a
    }

    override fun times(other: FractionOperator): FractionOperator {
        var addThis = wholeF * denominatorF
        var addOther = other.whole() * other.denominator()

        if (!(other.isPositive())) {
            addOther = -addOther
        }

        var thisN = numerator()
        var otherN = other.numerator()

        if (!(isPositive()))
            thisN = -thisN
        if (!(other.isPositive()))
            otherN = -otherN

        thisN = thisN + addThis
        otherN = otherN + addOther
        var newN = thisN * otherN
        var newD = denominatorF * other.denominator()

        var p  = positiveCheck(0, newN)
        var a : FractionOperator = porterajFraction(0,newN,newD,p)
        a.reduce()
        a.makeProper()
        return a
    }

    override fun times(other: Int): FractionOperator {
        var w = wholeF * other
        var n = numeratorF * other
        if (wholeF != 0 && other < 0) {
            n = -n
        }
        var p = positiveCheck(w, n)
        var a : FractionOperator = porterajFraction(w, n, denominatorF, p)
        a.reduce()
        a.makeProper()
        return a
    }

    override fun compareTo(other: FractionOperator): Int {
        if(positive != other.isPositive()) {
            if (positive) {
                return 1
            }
            else {
                return -1
            }
        }
        else {
            var a = this.toProper()
            var b = other.toProper()
            a.reduce()
            b.reduce()
            //a compared to b? return 1 if a is greater, neg 1 if b, and 0 if equal
            if(a.whole() != b.whole()) { //they have the same sign, so no sign checks needed
                var bw = b.whole()
                var aw = a.whole()
                if (!(a.isPositive())) {
                    aw = -aw
                }
                if(!(b.isPositive())) {
                    bw = -bw
                }
                if (bw > aw) {
                    return -1
                }
                else (aw > bw)
                    return 1
            }
            //the whole numbers are the same, we need to multiply across

            var addThis = wholeF * denominatorF
            var addOther = other.whole() * other.denominator()
            if (!(other.isPositive())) {
                addOther = -addOther
            }

            var thisN = numerator()
            var otherN = other.numerator()
            if(!(positive)) {
                thisN = -thisN
            }
            if (!(other.isPositive())) {
                otherN = -otherN
            }
            thisN = addThis + thisN
            otherN = otherN + addOther

            thisN = thisN * other.denominator()
            otherN = otherN * this.denominator()

            if (thisN > otherN) {
                return 1
            }
            else if (otherN > thisN) {
                return -1
            }
        }
        return 0
    }

    override fun equals(other: Any?): Boolean {
        other as FractionOperator
        var a = this.compareTo(other)
        return a == 0
    }

    override fun get(pos: Int): Optional<Int> {
        //You just return null?
        var optionalReturn: Optional<Int>
        optionalReturn = Optional.ofNullable(null)

        if(pos == 0) {
            if(wholeF != 0) {
                optionalReturn = Optional.of(wholeF)
                return optionalReturn
            }
            else {
                return optionalReturn
            }
        }
        else if (pos == 1) {
            if(numeratorF != 0) {
                optionalReturn = Optional.of(numeratorF)
                return optionalReturn
            }
            else {
                return optionalReturn
            }
        }
        else if (pos == 2) {
            if (denominatorF != 1) {
                optionalReturn = Optional.of(denominatorF)
                return optionalReturn
            }
            else {
                return optionalReturn
            }
        }
        else {
            return optionalReturn
        }
    }

    override fun invoke(len: Int): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isRepeating(): Optional<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun whole(): Int {
        return abs(wholeF)
    }
    override fun positiveCheck(whole: Int, num: Int) : Boolean {
        if (whole != 0) {
            return (whole >= 0)
        }
        else {
            return (num >= 0)
        }
    }
    fun fail (message :String) {
        throw IllegalArgumentException(message)
    }
    fun GCD(a: Int, b: Int): Int = if (b == 0) a else GCD(b, a % b)
}