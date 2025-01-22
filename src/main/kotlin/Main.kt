import java.io.File
import javax.sound.sampled.AudioSystem
import kotlin.system.exitProcess

fun main() {
    // Defino e inicializo un mapa (el diccionario de palabras/frutas):
    val frutas: Map<Int,String> = mapOf(
        Pair(1,"coco"),
        Pair(2,"pera"),
        Pair(3,"kiwi"),
        Pair(4,"piña"),
        Pair(5,"manzana"),
        Pair(6,"fresa"),
        Pair(7,"limon"),
        Pair(8,"banana"),
        Pair(9,"paraguayo"),
        Pair(10,"mandarina")
    )

    val rm = ReproductorMidi("ThrillingTheNoose.mid")
    println("Bienvenido al juego del ahorcado. Cargando el juego...")
    Thread.sleep(5000) // para dar impresión de que es un juego "tocho".
                             // también porque música tarda un ratito.

    println("Tu objetivo: adivinar un nombre de fruta (letra a letra)")
    val aleatorio = (1..10).random()
    val fruta = frutas.get(aleatorio) // esta es la fruta a acertar.
    // println(fruta)

    val frutaMap: MutableMap<Int,Char> = mutableMapOf()
    for (x in 1..fruta!!.length){
        frutaMap.set(x,fruta[x-1])
    }
    // println(frutaMap)

    print("Tu palabra con letras descubiertas por el momento es: ")
    val letras: MutableList<Char> = mutableListOf()
    for (x in 1..fruta.length){
        letras.add(x-1,'*')
    }
    println(letras.toString()) // esto sí que lo debe ver el usuario jugador.

    println("Llevas 0 fallos (máximo 7 fallos)")
    print("Introduce una letra que crees que está en la palabra secreta: ")
    var letra = readln()
    var caracter = letra[0]
    println("Has introducido la letra $caracter")

    var numeroDeFallos = 0
    //var descubiertoHastaAhora = letras // lista de asteriscos.
    while(numeroDeFallos < 7){
        if (frutaMap.containsValue(caracter)){ // cuando aciertas una letra

            for (x in 1..fruta.length){
                if (frutaMap.get(x) == caracter){letras.set(x-1,caracter)}
            }
            if (letras.contains('*') == false){
                println("Has ganado, has acertado la palabra")
                rm.parar()
                val victoriarm = ReproductorMidi("TheNooseCantBeatMe.mid")
                Thread.sleep(30000)
                exitProcess(0) // FIN DEL PROGRAMA
            }
        }
        else { // cuando no aciertas (la letra no está en la palabra a acertar)
            numeroDeFallos++
            println("Has fallado.")
            DibujoAhorcado.dibujar(numeroDeFallos)
            if (numeroDeFallos==7){
                println("Has perdido porque has fallado 7 veces.")
                rm.parar()
                // Así puedo hacer que suene mid y wav usando lenguaje kotlin:
                val clip = AudioSystem.getClip()
                val ais = AudioSystem.getAudioInputStream(File("BloodManiac.wav"))
                clip.open(ais)
                clip.start()
                Thread.sleep(125000) // gotta listen to the full song, it's amazing ;)
                exitProcess(0)
            }
        }
        println("Tu palabra con letras descubiertas por el momento es: ${letras.toString()}")
        print("LLevas $numeroDeFallos fallos. Introduce otra letra: ")
        letra = readln()
        caracter = letra[0]
        println("")
    } // nunca se avanzará más allá del bucle while tal como tengo escrito el código.
} // fin del main method.