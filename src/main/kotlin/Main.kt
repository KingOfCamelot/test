import kotlin.math.sqrt

import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.modules.SerializersModule

fun main() {

}

@kotlinx.serialization.Serializable
data class ColorOfRGBA(val red: Int, val green: Int, val blue: Int, val alpha: Double) {
    override fun toString(): String {
        return "Color figure of RGBA: $red, $green, $blue, $alpha"
    }
}

interface Shape2d {
    fun calcArea(): Double
}

interface ColoredShape2d : Shape2d {
    val borderColor: ColorOfRGBA
    val fillColor: ColorOfRGBA
}

val module = SerializersModule {
    polymorphic(ColoredShape2d::class) {
        subclass(Circle::class)
        subclass(Square::class)
        subclass(Triangle::class)
    }
}

@kotlinx.serialization.Serializable
class Circle(private val radius: Int, override val borderColor: ColorOfRGBA, override val fillColor: ColorOfRGBA) : ColoredShape2d
{
    override fun calcArea(): Double {
        return 3.14 * radius * radius
    }
}

@kotlinx.serialization.Serializable
class Square(private val firstSide: Double, private val secondSide: Double, override val borderColor: ColorOfRGBA, override val fillColor: ColorOfRGBA) : ColoredShape2d
{
    override fun calcArea(): Double {
        return firstSide * secondSide
    }
}

@kotlinx.serialization.Serializable
class Triangle(
    private val triangleFirstSide: Double,
    private val triangleSecondSide: Double,
    private val triangleThirdSide: Double,
    override val borderColor: ColorOfRGBA,
    override val fillColor: ColorOfRGBA
) : ColoredShape2d
{
    override fun calcArea(): Double {
        val perimeter = (triangleFirstSide + triangleSecondSide + triangleThirdSide) / 2
        return sqrt(
            perimeter * (perimeter - triangleFirstSide) * (perimeter - triangleSecondSide) * (perimeter - triangleThirdSide)
        )
    }
}

class ShapeCollector {
    private var figureList: ArrayList<ColoredShape2d> = arrayListOf()
    private var sumSquare: Double = 0.0
    fun addFigures(figure: ColoredShape2d) {
        figureList.add(figure)
    }

    fun listAllFigures(): ArrayList<ColoredShape2d> {
        return figureList
    }

    fun sumSquareFigures(): Double {
        val leftBorder = figureList.size - 1
        for (i in 0..leftBorder) sumSquare += figureList[i].calcArea()
        println(sumSquare)
        return sumSquare
    }

    fun sizeFigureList(): Int {
        return figureList.size
    }

    fun minSquare(): ColoredShape2d {
        val size = figureList.size - 1
        var minSquare = figureList[0].calcArea()
        var minFigure: ColoredShape2d = figureList[0]
        for (i in 0..size) {
            if (figureList[i].calcArea() <= minSquare) {
                minSquare = figureList[i].calcArea()
                minFigure = figureList[i]
            }
        }
        println("min: $minSquare")
        return minFigure
    }

    fun maxSquare(): ColoredShape2d {
        val size = figureList.size - 1
        var maxSquare = figureList[0].calcArea()
        var maxFigure: ColoredShape2d = figureList[0]
        for (i in 0..size) {
            if (figureList[i].calcArea() >= maxSquare) {
                maxSquare = figureList[i].calcArea()
                maxFigure = figureList[i]
            }
        }
        println("min: $maxSquare")
        return maxFigure
    }

    fun searchBorderColor(key: ColorOfRGBA): ArrayList<ColoredShape2d> {
        val listKeepFiguries: ArrayList<ColoredShape2d> = arrayListOf()
        val size = figureList.size - 1
        for (i in 0..size) {
            if (figureList[i].borderColor == key) {
                listKeepFiguries.add(figureList[i])
            }
        }
        return listKeepFiguries
    }

    fun mapFillColor(): Map<ColorOfRGBA, List<ColoredShape2d>> {
        return figureList.groupBy { it.fillColor }
    }

    fun mapBorderColor(): Map<ColorOfRGBA, List<ColoredShape2d>> {
        return figureList.groupBy { it.borderColor }
    }

    fun searchFillColor(key: ColorOfRGBA): ArrayList<ColoredShape2d> {
        val listKeepFiguries: ArrayList<ColoredShape2d> = arrayListOf()
        val size = figureList.size - 1
        for (i in 0..size) {
            if (figureList[i].fillColor == key) {
                listKeepFiguries.add(figureList[i])
            }
        }
        return listKeepFiguries
    }

    fun searchType(key: String): ArrayList<ColoredShape2d> {
        val typeList: ArrayList<ColoredShape2d> = arrayListOf()
        val size = figureList.size - 1
        for (i in 0..size) {
            if (figureList[i].javaClass.simpleName == key) {
                typeList.add(figureList[i])
            }
        }
        return typeList
    }
}