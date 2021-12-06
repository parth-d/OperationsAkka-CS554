import java.util.*

internal class RandomGen {
    fun createRandOrder(args: Array<String?>?): Hashtable<String, Int> {
        val order = Hashtable<String, Int>()
        val rand = Random()
        val low = 1
        val high = 100
        for (i in 0..2) {
            order.put("M"+i, rand.nextInt(high - low) + low)
        }
        val sortedKeys: List<String> = ArrayList(order.keys)
        Collections.sort(sortedKeys)
        return order
    }
}