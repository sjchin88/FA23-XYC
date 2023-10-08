import matplotlib.pyplot as plt

# Load the log file
with open('logs.csv', 'r') as f:
    lines = f.readlines()

# Extract start and end timestamps
start_time = int(lines[0].split(',')[0]) // 1000  # Convert to seconds
end_time = int(lines[-1].split(',')[0]) // 1000  # Convert to seconds
wall_time = end_time - start_time

# Create a list to track throughput for each second of the test
throughput = [0] * (wall_time + 1)

# Populate the throughput list
for line in lines:
    time, _, _, _ = line.split(',')
    second_elapsed = (int(time) // 1000) - start_time
    throughput[second_elapsed] += 1

# Plotting
x = list(range(0, wall_time + 1))
y = throughput
plt.plot(x, y, label='Throughput/second')
plt.xlabel('Seconds')
plt.ylabel('Throughput')
plt.title('Throughput over Time')
plt.legend()
plt.show()
