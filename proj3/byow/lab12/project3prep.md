# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer: My code relied on a lot of tedious calculation of how the initial x and y for each hexagon change in my hexworld.
Although I was able to finally pull off the tessellation, I felt humbled and educated after learning about the given lab code.

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer: Doing the tessellation exercise gives an insight into world generation as we need to randomly place and generate rooms and hallways at different points of our 2d array, given the rules laid out in Phase 3.

**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer: I would need a constructor for creating an empty world (a 2d array with just TileSet.Nothing), create a draw method, and like for the hexagons,
create a method for drawing "lines" of specific tiles abiding by certain conditions, etc. I would need methods for drawing rooms and hallways of given lengths/widths.

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: Rooms are rectangular while hallways are elongated. Rooms have width and height while hallways have lengths.
Both are generated using random dimensions. Hallways intersect, rooms don't. Hallways can turn. Both are drawn in very similar ways,
using the same tiles. Both are surrounded by walls (#).
